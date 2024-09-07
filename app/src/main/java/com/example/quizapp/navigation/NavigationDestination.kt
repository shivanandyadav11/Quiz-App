package com.example.quizapp.navigation

sealed class NavigationDestination(val route: String) {
    data object QuizLandingDestination: NavigationDestination("QuizLandingDestination")
    data object QuizQuestionsDestination: NavigationDestination("QuizQuestionsDestination")
    data object QuizResultDestination: NavigationDestination("QuizResultDestination")
}