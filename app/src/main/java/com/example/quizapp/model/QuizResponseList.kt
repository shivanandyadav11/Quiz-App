package com.example.quizapp.model

import com.google.gson.annotations.SerializedName

data class QuizResponseList(
    val quiz: List<Quiz> = emptyList()
)

data class Quiz(
    val correctAnswers: List<CorrectAnswer>,
    val id: String,
    val options: List<Option>,
    val question: String,
)

data class Option(
    val id: String,
    val text: String,
    val selected: Boolean = false,
)

data class CorrectAnswer(
    @SerializedName("id")
    val answerId: String
)