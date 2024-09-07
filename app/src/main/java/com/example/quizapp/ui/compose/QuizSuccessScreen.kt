package com.example.quizapp.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.quizapp.handler.QuizHandler
import com.example.quizapp.ui.viewModel.QuizViewModel

@Composable
internal fun QuizSuccessScreen(
    quizState: QuizViewModel.QuizListState.QuizSuccessView,
    quizHandler: QuizHandler,
) {
    Column {
        Text(text = "${quizState.score}")
    }
}