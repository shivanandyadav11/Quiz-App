package com.example.quizapp.service

import kotlinx.coroutines.flow.Flow

interface QuizRepo {
    suspend fun getQuizList(): Flow<QuizService.QuizResponse>
}