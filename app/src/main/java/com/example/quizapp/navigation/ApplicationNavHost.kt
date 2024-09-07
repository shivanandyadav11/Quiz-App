package com.example.quizapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun ApplicationNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.QuizLandingDestination.route
    ) {
        landingGraphDestination(
            navHostController = navController,
        )
        questionsGraphDestination(
            navHostController = navController,
        )
        resultGraphDestination(
            navHostController = navController,
        )
    }
}