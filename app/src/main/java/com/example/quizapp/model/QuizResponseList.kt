package com.example.quizapp.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the entire quiz response from the server.
 *
 * This class encapsulates a list of Quiz objects, representing all questions in a quiz.
 *
 * @property quiz A list of Quiz objects. Defaults to an empty list if not provided.
 */
data class QuizResponseList(
    val quiz: List<Quiz> = emptyList()
)

/**
 * Data class representing a single quiz question.
 *
 * This class contains all the information needed to display and evaluate a quiz question.
 *
 * @property correctAnswers A list of CorrectAnswer objects, representing the correct answer(s) for this question.
 * @property id A unique identifier for this quiz question.
 * @property options A list of Option objects, representing the available choices for this question.
 * @property question The text of the quiz question.
 */
data class Quiz(
    val correctAnswers: List<CorrectAnswer>,
    val id: String,
    val options: List<Option>,
    val question: String,
)

/**
 * Data class representing a single answer option for a quiz question.
 *
 * @property id A unique identifier for this option.
 * @property text The text content of this option.
 * @property selected A boolean indicating whether this option has been selected by the user. Defaults to false.
 */
data class Option(
    val id: String,
    val text: String,
    var selected: Boolean = false,
)

/**
 * Data class representing a correct answer for a quiz question.
 *
 * @property answerId The ID of the correct answer option. Note that this is serialized as "id" in JSON.
 */
data class CorrectAnswer(
    @SerializedName("id")
    val answerId: String
)