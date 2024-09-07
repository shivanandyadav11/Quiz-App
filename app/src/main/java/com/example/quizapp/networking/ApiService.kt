package com.example.quizapp.networking

import com.example.quizapp.model.QuizResponseList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("quizList")
    suspend fun getQuizList(): Response<QuizResponseList>
}