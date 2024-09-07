package com.example.quizapp.networking

import com.example.quizapp.model.QuizAnswer
import com.example.quizapp.model.QuizAttemptedResponse
import com.example.quizapp.model.QuizResponseList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("quizList")
    suspend fun getQuizList(): Response<QuizResponseList>

    @POST("/sendQuiz")
    suspend fun sendAttemptedAnswer(@Body quizAnswered: QuizAnswer): Response<QuizAttemptedResponse>
}