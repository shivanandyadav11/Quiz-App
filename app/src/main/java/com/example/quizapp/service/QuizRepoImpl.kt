package com.example.quizapp.service

import com.example.quizapp.model.QuizAnswer
import com.example.quizapp.model.QuizResponseList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of the [QuizRepo] interface.
 *
 * This class provides the concrete implementation of quiz-related data operations,
 * delegating the actual network calls to the [QuizService].
 *
 * @property quizService The [QuizService] instance used for making API calls.
 */
class QuizRepoImpl @Inject constructor(private val quizService: QuizService) : QuizRepo {

    /**
     * Retrieves the list of quizzes from the [QuizService].
     *
     * @return A Flow emitting [QuizService.QuizResponse] of [QuizResponseList], which can be either a Success
     *         containing the quiz list or a Failure indicating an error occurred.
     */
    override suspend fun getQuizList(): Flow<QuizService.QuizResponse<QuizResponseList>> {
        return quizService.getQuizList()
    }

    /**
     * Sends an attempted answer for a quiz question to the [QuizService].
     *
     * This method uses the IO dispatcher to perform the network operation off the main thread.
     *
     * @param quizAnswered The [QuizAnswer] object representing the user's answer.
     * @return A Flow emitting [QuizService.QuizResponse] of [Boolean], which indicates
     *         whether the answer was successfully sent and processed.
     */
    override suspend fun sendAttemptedAnswer(quizAnswered: QuizAnswer): Flow<QuizService.QuizResponse<Boolean>> {
        return withContext(Dispatchers.IO) {
            quizService.sendAttemptedAnswer(quizAnswered)
        }
    }
}