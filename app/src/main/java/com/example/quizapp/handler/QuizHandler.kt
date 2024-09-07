package com.example.quizapp.handler

/**
 * Handler class for managing quiz-related user interactions.
 *
 * This class encapsulates the callback functions for various user actions
 * during the quiz flow. It provides a clean way to handle user interactions
 * without tightly coupling the UI components to the underlying logic.
 *
 * @property onStartClick Function to be called when the user starts the quiz.
 *                        Defaults to an empty function if not provided.
 * @property onNextClick Function to be called when the user moves to the next question.
 *                       Defaults to an empty function if not provided.
 * @property onStartAgain Function to be called when the user chooses to restart the quiz.
 *                        Defaults to an empty function if not provided.
 */
class QuizHandler(
    val onStartClick: () -> Unit = {},
    val onNextClick: () -> Unit = {},
    val onStartAgain: () -> Unit = {}
)