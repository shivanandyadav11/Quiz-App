package com.example.quizapp.ui.compose

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.viewModel.QuizViewModel
import com.example.quizapp.ui.viewModel.QuizViewModel.QuizListState

@Composable
internal fun QuizScreen(modifier: Modifier = Modifier, viewModel: QuizViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getQuizList()
    }
    val quizList by viewModel.quizList.collectAsState()

    when(quizList) {
        is QuizListState.Loading -> {
            CircularProgressIndicator()
        }

        is QuizListState.Failure -> {
            Text(text = (quizList as QuizListState.Failure).error)
        }
        is QuizListState.Success -> {
            Text(text = (quizList as QuizListState.Success).quizList.quiz?.get(0)?.question.orEmpty())
        }
    }
}