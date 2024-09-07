package com.example.quizapp.model

/**
 * Data class representing an answer to a quiz question.
 *
 * This class encapsulates the information related to a user's answer
 * to a specific quiz question, including whether the answer was correct
 * and how long it took the user to answer.
 *
 * @property id The unique identifier of the question being answered.
 * @property correctAnswer Indicates whether the user's answer was correct.
 *                         Defaults to false if not specified.
 * @property timeTaken A string representation of the time taken to answer the question.
 *                     Defaults to an empty string if not specified.
 */
data class QuizAnswer(
    val id: String,
    val correctAnswer: Boolean = false,
    val timeTaken: String = ""
)