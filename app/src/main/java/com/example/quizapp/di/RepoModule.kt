package com.example.quizapp.di

import com.example.quizapp.service.QuizRepo
import com.example.quizapp.service.QuizRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module for providing repository-related dependencies.
 *
 * This module is responsible for binding the implementation of the QuizRepo
 * interface to its concrete implementation, QuizRepoImpl. It uses Hilt's @Binds
 * annotation to efficiently provide this dependency.
 *
 * The module is installed in the SingletonComponent, ensuring that the provided
 * repository instance is a singleton and available throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    /**
     * Binds the QuizRepoImpl to the QuizRepo interface.
     *
     * This method uses Hilt's @Binds annotation to efficiently tell Hilt how to provide
     * instances of QuizRepo when they are requested as dependencies. It binds the interface
     * QuizRepo to its implementation QuizRepoImpl.
     *
     * @param quizRepoImpl The concrete implementation of QuizRepo.
     * @return An instance of QuizRepo, which is actually an instance of QuizRepoImpl.
     */
    @Binds
    fun bindQuizRepo(quizRepoImpl: QuizRepoImpl): QuizRepo
}