package com.example.quizapp.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.handler.QuizHandler
import com.example.quizapp.ui.compose.widget.LandingTopBarHeader
import com.example.quizapp.ui.viewModel.QuizViewModel
import com.example.quizapp.ui.viewModel.QuizViewModel.QuizListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun QuizScreen(viewModel: QuizViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getQuizList()
    }
    val quizState by viewModel.quizUIState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    LandingTopBarHeader(
                        text = quizState.toolBarTitle,
                        isIconNeeded = quizState.needIcon
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        QuizScreenImpl(
            quizState = quizState,
            quizHandler = QuizHandler(
                onStartClick = {
                    viewModel.startQuiz()
                },
                onNextClick = {
                    viewModel.getNextQuiz()
                },
                onStartAgain = {
                    viewModel.startQuizAgain()
                }
            ),
            onSelectAnswer = {
                viewModel.onOptionSelection(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun QuizScreenImpl(
    quizState: QuizListState,
    quizHandler: QuizHandler,
    onSelectAnswer: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (quizState) {
            is QuizListState.QuizLandingView -> {
                QuizLandingScreen(quizHandler = quizHandler)
            }

            is QuizListState.QuizQuestionView -> {
                QuizQuestionScreen(
                    quizState = quizState,
                    quizHandler = quizHandler,
                    onSelectAnswer = { onSelectAnswer(it) },
                )
            }

            is QuizListState.QuizSuccessView -> {
                QuizSuccessScreen(
                    quizState = quizState,
                    quizHandler = quizHandler,
                )
            }
        }
    }
}