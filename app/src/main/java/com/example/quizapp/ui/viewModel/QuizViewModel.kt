package com.example.quizapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.QuizResponseList
import com.example.quizapp.service.QuizRepo
import com.example.quizapp.service.QuizService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepo: QuizRepo) : ViewModel() {

    private val _quizList = MutableStateFlow<QuizListState>(QuizListState.Loading)
    internal val quizList: StateFlow<QuizListState> = _quizList.asStateFlow()

    suspend fun getQuizList() = viewModelScope.launch(Dispatchers.IO) {
        _quizList.emit(QuizListState.Loading)
        when (val response = quizRepo.getQuizList().first()) {
            is QuizService.QuizResponse.Success -> {
                _quizList.emit(
                    QuizListState.Success(response.quizList)
                )
            }

            is QuizService.QuizResponse.Failure -> {
                _quizList.emit(
                    QuizListState.Failure(response.error)
                )
            }
        }
    }


    sealed interface QuizListState {
        data object Loading : QuizListState
        data class Success(val quizList: QuizResponseList) : QuizListState
        data class Failure(val error: String) : QuizListState
    }
}