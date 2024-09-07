package com.example.quizapp.handler

class QuizHandler(
    val onStartClick: () -> Unit = {},
    val onNextClick: (id: String, isAnswerCorrect: Boolean) -> Unit = { _, _ -> },
    val onStartAgain: () -> Unit = {},
)
