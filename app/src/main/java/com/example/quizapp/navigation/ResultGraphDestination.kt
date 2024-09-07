package com.example.quizapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.resultGraphDestination(
    navHostController: NavHostController,
) {
    composable(NavigationDestination.QuizResultDestination.route) {

    }
}