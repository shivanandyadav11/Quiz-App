package com.example.quizapp.service

import com.example.quizapp.model.QuizResponseList
import com.example.quizapp.networking.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class QuizService @Inject constructor(private val apiService: ApiService) {
    suspend fun getQuizList(): Flow<QuizResponse> {
        return flow {
            val response = apiService.getQuizList()
            emit(handleResponse(response))
        }.catch { error ->
            emit(QuizResponse.Failure(error.message ?: "Something went wrong"))
        }
    }

    private fun handleResponse(response: Response<QuizResponseList>): QuizResponse {
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                QuizResponse.Success(responseBody)
            } else {
                QuizResponse.Failure("QuizList response body is null")
            }
        } else {
            QuizResponse.Failure(response.message())
        }
    }


    sealed class QuizResponse {
        data class Success(val quizList: QuizResponseList) : QuizResponse()
        data class Failure(val error: String) : QuizResponse()
    }
}