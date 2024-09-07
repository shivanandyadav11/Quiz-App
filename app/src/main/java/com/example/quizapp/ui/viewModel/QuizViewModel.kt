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

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepo: QuizRepo) : ViewModel() {

    private val _quizList = MutableStateFlow<QuizResponseList?>(null)
    private val quizAnswers = mutableListOf<QuizAnswer>()

    private val quizUiMutableState = MutableStateFlow<QuizListState>(QuizListState.QuizLandingView)
    internal val quizUIState: StateFlow<QuizListState> = quizUiMutableState.asStateFlow()

    suspend fun getQuizList() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = quizRepo.getQuizList().first()) {
            is QuizService.QuizResponse.Success -> {
                _quizList.emit(response.quizList)
            }

            is QuizService.QuizResponse.Failure -> {
                _quizList.emit(null)
            }
        }
    }

    fun startQuiz() {
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

    internal fun getNextQuiz() = viewModelScope.launch {
        val currentState = quizUIState.value
        if (currentState is QuizListState.QuizQuestionView) {
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
                            val questionNumber = currentState.questionNumber + 1
                            updateUIState(
                                QuizListState.QuizQuestionView(
                                    quizQuestion = _quizList.value?.quiz?.get(questionNumber),
                                    questionNumber = questionNumber,
                                    totalQuestions = _quizList.value?.quiz?.size ?: 0,
                                )
                            )
                        }

                        QuizService.QuizAttemptResponse.Failure -> Unit
                    }
                }
            }
        }
    }

    private fun isCorrectAnswer(currentState: QuizListState.QuizQuestionView): Boolean {
        val quiz = currentState.quizQuestion
        // Get all selected option IDs
        val selectedOptionIds = quiz?.options?.filter { it.selected }?.map { it.id }

        // Get all correct answer IDs
        val correctAnswerIds = quiz?.correctAnswers?.map { it.answerId }

        // Check if the sets are equal
        val isCorrect = selectedOptionIds?.toSet() == correctAnswerIds?.toSet()

        return isCorrect
    }

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
                    currentState.quizQuestion?.copy(
                        options = updatedOptions ?: emptyList()
                    )
                )
            )
        }
    }

    private fun updateUIState(state: QuizListState) {
        quizUiMutableState.update { state }
    }

    @Stable
    sealed class QuizListState(val toolBarTitle: String, val needIcon: Boolean = false) {
        data object QuizLandingView : QuizListState(toolBarTitle = "Home", needIcon = true)
        data class QuizQuestionView(
            val quizQuestion: Quiz?,
            val questionNumber: Int = 0,
            val totalQuestions: Int = 0,
            val startTime: String = "",
            val submissionError: Boolean = false,
        ) : QuizListState("Questions")

        data class QuizSuccessView(
            val score: Int,
            val correctAnswer: Int,
            val wrongAnswer: Int
        ) : QuizListState("Result")
    }
}