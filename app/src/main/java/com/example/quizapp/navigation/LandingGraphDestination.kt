package com.example.quizapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.quizapp.ui.compose.QuizLandingScreen
import com.example.quizapp.ui.viewModel.QuizViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.landingGraphDestination(
    navHostController: NavHostController,
) {
    composable(NavigationDestination.QuizLandingDestination.route) {
        val viewModel: QuizViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            scope.launch {
                viewModel.getQuizList()
            }
        }
        QuizLandingScreen(
            onStartClick = {

            }
        )
    }
}