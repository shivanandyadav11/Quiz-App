package com.example.quizapp.di

import com.example.quizapp.networking.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * Hilt module for providing network-related dependencies.
 *
 * This module configures and provides instances of OkHttpClient, Retrofit, and ApiService.
 * It also includes configuration for SSL certificate validation (currently set to ignore all SSL errors).
 *
 * WARNING: The current SSL configuration is not secure and should not be used in production.
 * It's designed for development or testing purposes only.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides the base URL for the API.
     *
     * @return The base URL string for the API.
     */
    @Provides
    fun provideBaseUrl(): String = "https://quix.online/"

    /**
     * Provides an OkHttpClient instance with logging and SSL error ignoring configuration.
     *
     * WARNING: The SSL error ignoring configuration is not secure and should not be used in production.
     *
     * @return Configured OkHttpClient instance.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .ignoreAllSSLErrors()
            .build()
    }

    /**
     * Extension function to configure OkHttpClient.Builder to ignore all SSL errors.
     *
     * WARNING: This configuration is not secure and should not be used in production.
     * It's designed for development or testing purposes only.
     *
     * @return The modified OkHttpClient.Builder instance.
     */
    private fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("SSL").apply {
            init(null, arrayOf(naiveTrustManager), null)
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier { _, _ -> true }
        return this
    }

    /**
     * Provides a Retrofit instance configured with the base URL, OkHttpClient, and necessary factories.
     *
     * @param okHttpClient The OkHttpClient instance to use.
     * @param baseUrl The base URL for the API.
     * @return Configured Retrofit instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    /**
     * Provides an instance of the ApiService interface.
     *
     * @param retrofit The Retrofit instance to use for creating the API service.
     * @return Implementation of the ApiService interface.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}