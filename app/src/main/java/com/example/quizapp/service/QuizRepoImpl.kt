package com.example.quizapp.service

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizRepoImpl @Inject constructor(private val quizService: QuizService) : QuizRepo {
    override suspend fun getQuizList(): Flow<QuizService.QuizResponse> {
        return quizService.getQuizList()
    }
}