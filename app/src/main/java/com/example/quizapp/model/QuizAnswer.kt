package com.example.quizapp.model

data class QuizAnswer(
    val id: String,
    val correctAnswer: Boolean = false,
    val timeTaken: String,
)
