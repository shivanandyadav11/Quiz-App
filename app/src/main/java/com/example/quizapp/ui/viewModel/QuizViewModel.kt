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

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepo: QuizRepo) : ViewModel() {

    private val _quizList = MutableStateFlow<QuizResponseList?>(null)
    private val _quizAnswers = MutableStateFlow<List<QuizAnswer>>(emptyList())

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
                    quizQuestion =  _quizList.value?.quiz?.get(0),
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