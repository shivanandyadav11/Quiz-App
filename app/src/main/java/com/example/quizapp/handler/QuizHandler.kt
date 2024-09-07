package com.example.quizapp.handler

class QuizHandler(
    val onStartClick: () -> Unit = {},
    val onNextClick: () -> Unit = {},
    val onStartAgain: () -> Unit = {},
)
