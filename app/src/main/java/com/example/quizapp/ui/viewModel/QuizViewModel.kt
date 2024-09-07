package com.example.quizapp.ui.viewModel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.Quiz
import com.example.quizapp.model.QuizAnswer
import com.example.quizapp.model.QuizResponseList
import com.example.quizapp.service.QuizRepo
import com.example.quizapp.service.QuizService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * ViewModel for managing the quiz application's state and business logic.
 *
 * This ViewModel is responsible for fetching quiz data, managing user interactions,
 * and updating the UI state throughout the quiz flow.
 *
 * @property quizRepo The repository for fetching quiz data and submitting answers.
 */
@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepo: QuizRepo) : ViewModel() {

    /** Holds the list of quiz questions. */
    private val _quizList = MutableStateFlow<QuizResponseList?>(null)

    /** Stores the user's answers to quiz questions. */
    private val quizAnswers = mutableListOf<QuizAnswer>()

    /** The mutable state flow for the quiz UI state. */
    private val quizUiMutableState = MutableStateFlow<QuizListState>(QuizListState.QuizLandingView)

    /** The public state flow exposing the current quiz UI state. */
    internal val quizUIState: StateFlow<QuizListState> = quizUiMutableState.asStateFlow()

    /**
     * Fetches the quiz list from the repository.
     *
     * This function is suspended and should be called from a coroutine scope.
     */
    internal suspend fun getQuizList() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = quizRepo.getQuizList().first()) {
            is QuizService.QuizResponse.Success -> {
                _quizList.emit(response.quizList)
            }
            is QuizService.QuizResponse.Failure -> {
                _quizList.emit(null)
            }
        }
    }

    /**
     * Initiates the quiz by updating the UI state to show the first question.
     */
    internal fun startQuiz() {
        val currentState = quizUIState.value
        if (currentState == QuizListState.QuizLandingView) {
            updateUIState(
                QuizListState.QuizQuestionView(
                    quizQuestion = _quizList.value?.quiz?.get(0),
                    questionNumber = 0,
                    totalQuestions = _quizList.value?.quiz?.size ?: 0,
                )
            )
        }
    }

    /**
     * Processes the current answer and moves to the next question or quiz completion.
     *
     * This function submits the user's answer, updates the quiz state, and either
     * shows the next question or the final quiz results.
     */
    internal fun getNextQuiz() = viewModelScope.launch {
        val currentState = quizUIState.value
        if (currentState is QuizListState.QuizQuestionView) {
            val optionSelected =
                currentState.quizQuestion?.options?.find { it.selected }?.selected
            if (optionSelected == true) {
                val result = isCorrectAnswer(currentState)
                val quizAnswered = QuizAnswer(
                    id = currentState.quizQuestion.id,
                    correctAnswer = result
                )
                quizAnswers.add(quizAnswered)
                val response = quizRepo.sendAttemptedAnswer(quizAnswered).first()
                when (response) {
                    is QuizService.QuizAttemptResponse.Success -> {
                        if (_quizList.value?.quiz?.size == currentState.questionNumber + 1) {
                            val correctAnswer = quizAnswers.count { it.correctAnswer }
                            updateUIState(
                                QuizListState.QuizSuccessView(
                                    score = ((correctAnswer.toDouble() / quizAnswers.size.toDouble()) * 100).roundToInt(),
                                    correctAnswer = correctAnswer,
                                    wrongAnswer = quizAnswers.size - correctAnswer,
                                )
                            )
                        } else {
                            val questionNumber = currentState.questionNumber + 1
                            updateUIState(
                                QuizListState.QuizQuestionView(
                                    quizQuestion = _quizList.value?.quiz?.get(questionNumber),
                                    questionNumber = questionNumber,
                                    totalQuestions = _quizList.value?.quiz?.size ?: 0,
                                )
                            )
                        }
                    }
                    QuizService.QuizAttemptResponse.Failure -> Unit
                }
            }
        }
    }

    /**
     * Resets the quiz state to allow the user to start over.
     */
    internal fun startQuizAgain() {
        val currentState = quizUIState.value
        if (currentState is QuizListState.QuizSuccessView) {
            quizAnswers.clear()
            updateUIState(QuizListState.QuizLandingView)
        }
    }

    /**
     * Updates the selected state of an option when the user makes a selection.
     *
     * @param id The ID of the selected option.
     */
    internal fun onOptionSelection(id: String) {
        val currentState = quizUIState.value
        if (currentState is QuizListState.QuizQuestionView) {
            val updatedOptions = currentState.quizQuestion?.options?.map { option ->
                if (option.id == id) {
                    option.copy(selected = !option.selected)
                } else {
                    option.copy(selected = option.selected)
                }
            }
            updateUIState(
                currentState.copy(
                    quizQuestion = currentState.quizQuestion?.copy(
                        options = updatedOptions ?: emptyList()
                    )
                )
            )
        }
    }

    /**
     * Updates the UI state with a new state.
     *
     * @param state The new QuizListState to be set.
     */
    private fun updateUIState(state: QuizListState) {
        quizUiMutableState.update { state }
    }

    /**
     * Checks if the current answer is correct.
     *
     * @param currentState The current QuizQuestionView state.
     * @return Boolean indicating whether the answer is correct.
     */
    private fun isCorrectAnswer(currentState: QuizListState.QuizQuestionView): Boolean {
        val quiz = currentState.quizQuestion
        // Get all selected option IDs
        val selectedOptionIds = quiz?.options?.filter { it.selected }?.map { it.id }

        // Get all correct answer IDs
        val correctAnswerIds = quiz?.correctAnswers?.map { it.answerId }

        // Check if the sets are equal
        return selectedOptionIds?.toSet() == correctAnswerIds?.toSet()
    }

    /**
     * Sealed class representing different states of the quiz UI.
     *
     * @property toolBarTitle The title to be displayed in the toolbar.
     * @property needIcon Boolean indicating whether an icon is needed in the toolbar.
     */
    @Stable
    sealed class QuizListState(val toolBarTitle: String, val needIcon: Boolean = false) {
        /** Represents the initial landing view of the quiz. */
        data object QuizLandingView : QuizListState(toolBarTitle = "Home", needIcon = true)

        /**
         * Represents the state when a question is being displayed.
         *
         * @property quizQuestion The current question being displayed.
         * @property questionNumber The current question number.
         * @property totalQuestions The total number of questions in the quiz.
         * @property startTime The start time of the quiz (currently not used).
         * @property submissionError Indicates if there was an error in submission.
         */
        data class QuizQuestionView(
            val quizQuestion: Quiz?,
            val questionNumber: Int = 0,
            val totalQuestions: Int = 0,
            val startTime: String = "",
            val submissionError: Boolean = false,
        ) : QuizListState("Questions")

        /**
         * Represents the final state showing the quiz results.
         *
         * @property score The final score of the quiz.
         * @property correctAnswer The number of correct answers.
         * @property wrongAnswer The number of wrong answers.
         */
        data class QuizSuccessView(
            val score: Int,
            val correctAnswer: Int,
            val wrongAnswer: Int
        ) : QuizListState("Result")
    }
}