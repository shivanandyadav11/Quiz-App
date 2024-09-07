package com.example.quizapp.di

import com.example.quizapp.service.QuizRepo
import com.example.quizapp.service.QuizRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindQuiZRepo(quizRepoImpl: QuizRepoImpl): QuizRepo
}