package com.example.quizapp.handler

class QuizHandler(
    val onStartClick: () -> Unit = {},
    val onNextClick: (id: String, isAnswerCorrect: Boolean, questionNumber: Int) -> Unit = { _, _,_ -> },
    val onStartAgain: () -> Unit = {},
)
