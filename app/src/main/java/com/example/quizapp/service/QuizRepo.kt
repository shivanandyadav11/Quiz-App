package com.example.quizapp.service

import com.example.quizapp.model.QuizAnswer
import com.example.quizapp.model.QuizResponseList
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for quiz-related data operations.
 *
 * This interface abstracts the data source for quiz operations, allowing for
 * easy swapping of implementations (e.g., remote API, local database) without
 * affecting the rest of the application.
 */
interface QuizRepo {

    /**
     * Retrieves the list of quizzes.
     *
     * This method fetches the quiz data, potentially from a remote source or local cache.
     *
     * @return A Flow emitting [QuizService.QuizResponse] of [QuizResponseList], which can be either a Success
     *         containing the quiz list or a Failure indicating an error occurred.
     */
    suspend fun getQuizList(): Flow<QuizService.QuizResponse<QuizResponseList>>

    /**
     * Sends an attempted answer for a quiz question.
     *
     * This method submits the user's answer to a quiz question, potentially to a remote server.
     *
     * @param quizAnswered The [QuizAnswer] object representing the user's answer.
     * @return A Flow emitting [QuizService.QuizResponse] of [Boolean], which indicates
     *         whether the answer was successfully sent and processed.
     */
    suspend fun sendAttemptedAnswer(quizAnswered: QuizAnswer): Flow<QuizService.QuizResponse<Boolean>>
}