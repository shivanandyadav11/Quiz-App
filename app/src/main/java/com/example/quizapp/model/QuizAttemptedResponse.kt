package com.example.quizapp.model

/**
 * Data class representing the response after a quiz attempt is submitted.
 *
 * This class encapsulates the server's response indicating whether
 * the submitted quiz attempt was successfully received and processed.
 *
 * @property sent A boolean indicating whether the quiz attempt was successfully
 *                sent to and processed by the server. True if the attempt was
 *                successfully processed, false otherwise.
 */
data class QuizAttemptedResponse(
    val sent: Boolean
)