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
 * processing the responses, and wrapping them in appropriate result types.
 *
 * @property apiService The [ApiService] instance used for making API calls.
 */
class QuizService @Inject constructor(private val apiService: ApiService) {

    /**
     * Fetches the quiz list from the API.
     *
     * @return A [Flow] emitting [QuizResponse] which can be either Success or Failure.
     */
    suspend fun getQuizList(): Flow<QuizResponse> {
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
     * @return A [Flow] emitting [QuizAttemptResponse] which can be either Success or Failure.
     */
    suspend fun sendAttemptedAnswer(quizAnswered: QuizAnswer): Flow<QuizAttemptResponse> {
        return flow {
            val response = apiService.sendAttemptedAnswer(quizAnswered)
            emit(handleAttemptedResponse(response))
        }.catch {
            emit(QuizAttemptResponse.Failure)
        }
    }

    /**
     * Handles the response from the attempted answer API call.
     *
     * @param response The [Response] from the API call.
     * @return [QuizAttemptResponse] indicating Success or Failure.
     */
    private fun handleAttemptedResponse(response: Response<QuizAttemptedResponse>): QuizAttemptResponse {
        return if (response.isSuccessful) {
            QuizAttemptResponse.Success
        } else {
            QuizAttemptResponse.Failure
        }
    }

    /**
     * Handles the response from the quiz list API call.
     *
     * @param response The [Response] from the API call.
     * @return [QuizResponse] wrapping the quiz list or an error message.
     */
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

    /**
     * Sealed class representing the possible responses from the quiz list API call.
     */
    sealed class QuizResponse {
        /**
         * Represents a successful response containing the quiz list.
         *
         * @property quizList The [QuizResponseList] retrieved from the API.
         */
        data class Success(val quizList: QuizResponseList) : QuizResponse()

        /**
         * Represents a failed response with an error message.
         *
         * @property error The error message describing the failure reason.
         */
        data class Failure(val error: String) : QuizResponse()
    }

    /**
     * Sealed interface representing the possible responses from the attempted answer API call.
     */
    sealed interface QuizAttemptResponse {
        /**
         * Represents a successful attempt submission.
         */
        data object Success : QuizAttemptResponse

        /**
         * Represents a failed attempt submission.
         */
        data object Failure : QuizAttemptResponse
    }
}