package com.example.quizapp.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.viewModel.QuizViewModel

@Composable
internal fun QuizLandingScreen(
    viewModel: QuizViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}