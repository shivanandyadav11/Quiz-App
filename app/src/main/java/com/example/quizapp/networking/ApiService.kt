package com.example.quizapp.networking

import com.example.quizapp.model.QuizAnswer
import com.example.quizapp.model.QuizAttemptedResponse
import com.example.quizapp.model.QuizResponseList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface defining the API endpoints for the Quiz application.
 *
 * This interface uses Retrofit annotations to declare the HTTP methods
 * and their corresponding endpoint paths. It provides methods for
 * fetching the quiz list and sending attempted answers.
 */
interface ApiService {

    /**
     * Fetches the list of quizzes from the server.
     *
     * @return A [Response] object wrapping the [QuizResponseList].
     *         The response contains the list of quizzes if the request is successful.
     */
    @GET("quizList")
    suspend fun getQuizList(): Response<QuizResponseList>

    /**
     * Sends an attempted answer to the server.
     *
     * @param quizAnswered The [QuizAnswer] object containing the user's answer.
     * @return A [Response] object wrapping the [QuizAttemptedResponse].
     *         The response contains the server's feedback on the attempted answer.
     */
    @POST("/sendQuiz")
    suspend fun sendAttemptedAnswer(@Body quizAnswered: QuizAnswer): Response<QuizAttemptedResponse>
}