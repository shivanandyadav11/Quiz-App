package com.example.quizapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for the Quiz app.
 *
 * This class serves as the base application class for the entire app and is
 * annotated with @HiltAndroidApp to enable Hilt dependency injection.
 *
 * The @HiltAndroidApp annotation triggers Hilt's code generation, including
 * a base class for the application that serves as the application-level
 * dependency container.
 *
 * This class doesn't need to implement any additional logic as all the
 * necessary setup for Hilt is done by the @HiltAndroidApp annotation.
 */
@HiltAndroidApp
class BaseApplication : Application()