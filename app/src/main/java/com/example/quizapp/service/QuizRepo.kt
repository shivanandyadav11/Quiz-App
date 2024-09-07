package com.example.quizapp.service

import com.example.quizapp.model.QuizAnswer
import kotlinx.coroutines.flow.Flow

interface QuizRepo {
    suspend fun getQuizList(): Flow<QuizService.QuizResponse>
    suspend fun sendAttemptedAnswer(quizAnswered: QuizAnswer): Flow<QuizService.QuizAttemptResponse>
}