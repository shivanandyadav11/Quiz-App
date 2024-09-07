package com.example.quizapp.service

import com.example.quizapp.model.QuizAnswer
import com.example.quizapp.service.QuizService.QuizAttemptResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizRepoImpl @Inject constructor(private val quizService: QuizService) : QuizRepo {
    override suspend fun getQuizList(): Flow<QuizService.QuizResponse> {
        return quizService.getQuizList()
    }

    override suspend fun sendAttemptedAnswer(quizAnswered: QuizAnswer) : Flow<QuizAttemptResponse>{
        return withContext(Dispatchers.IO) {
            quizService.sendAttemptedAnswer(quizAnswered)
        }
    }
}