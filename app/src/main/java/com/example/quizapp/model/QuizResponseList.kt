package com.example.quizapp.model

data class QuizResponseList(
    val quiz: List<Quiz?>? = null
)

data class Quiz(
    val correctAnswers: List<String?>? = null,
    val id: String? = null,
    val options: List<Option?>? = null,
    val question: String? = null
)

data class Option(
    val id: String? = null,
    val text: String? = null
)