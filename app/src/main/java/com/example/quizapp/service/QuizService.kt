package com.example.quizapp.service

import com.example.quizapp.model.QuizAnswer
import com.example.quizapp.model.QuizAttemptedResponse
import com.example.quizapp.model.QuizResponseList
import com.example.quizapp.networking.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

/**
 * Service class for handling quiz-related API calls and response processing.
 *
 * This class encapsulates the logic for making API calls using [ApiService],
 * processing the responses, and wrapping them in a generic [QuizResponse] type.
 *
 * @property apiService The [ApiService] instance used for making API calls.
 */
class QuizService @Inject constructor(private val apiService: ApiService) {

    /**
     * Fetches the quiz list from the API.
     *
     * @return A [Flow] emitting [QuizResponse] of [QuizResponseList] which can be either Success or Failure.
     */
    suspend fun getQuizList(): Flow<QuizResponse<QuizResponseList>> {
        return flow {
            val response = apiService.getQuizList()
            emit(handleResponse(response))
        }.catch { error ->
            emit(QuizResponse.Failure(error.message ?: "Something went wrong"))
        }
    }

    /**
     * Sends an attempted answer to the API.
     *
     * @param quizAnswered The [QuizAnswer] to be sent to the API.
     * @return A [Flow] emitting [QuizResponse] of [Boolean] which can be either Success or Failure.
     */
    suspend fun sendAttemptedAnswer(quizAnswered: QuizAnswer): Flow<QuizResponse<Boolean>> {
        return flow {
            val response = apiService.sendAttemptedAnswer(quizAnswered)
            emit(handleAttemptedResponse(response))
        }.catch { error ->
            emit(QuizResponse.Failure(error.message ?: "Failed to send answer"))
        }
    }

    /**
     * Handles the response from the attempted answer API call.
     *
     * @param response The [Response] from the API call.
     * @return [QuizResponse] of [Boolean] indicating Success or Failure.
     */
    private fun handleAttemptedResponse(response: Response<QuizAttemptedResponse>): QuizResponse<Boolean> {
        return if (response.isSuccessful) {
            QuizResponse.Success(true)
        } else {
            QuizResponse.Failure("Failed to send answer: ${response.message()}")
        }
    }

    /**
     * Handles the response from the quiz list API call.
     *
     * @param response The [Response] from the API call.
     * @return [QuizResponse] of [QuizResponseList] wrapping the quiz list or an error message.
     */
    private fun handleResponse(response: Response<QuizResponseList>): QuizResponse<QuizResponseList> {
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

    /**
     * Sealed class representing the possible responses from API calls.
     *
     * @param T The type of data in case of a successful response.
     */
    sealed class QuizResponse<out T> {
        /**
         * Represents a successful response containing data of type [T].
         *
         * @property data The data retrieved from the API.
         */
        data class Success<T>(val data: T) : QuizResponse<T>()

        /**
         * Represents a failed response with an error message.
         *
         * @property error The error message describing the failure reason.
         */
        data class Failure(val error: String) : QuizResponse<Nothing>()
    }
}