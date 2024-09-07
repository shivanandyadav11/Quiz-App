package com.example.quizapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.handler.QuizHandler
import com.example.quizapp.ui.compose.widget.AnswerOptions
import com.example.quizapp.ui.compose.widget.ProgressIndicator
import com.example.quizapp.ui.compose.widget.QuizButton
import com.example.quizapp.ui.viewModel.QuizViewModel

@Composable
internal fun QuizQuestionScreen(
    quizState: QuizViewModel.QuizListState.QuizQuestionView,
    quizHandler: QuizHandler,
    onSelectAnswer: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue),
            contentAlignment = Alignment.TopCenter,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = quizState.quizQuestion?.question.orEmpty(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    AnswerOptions(
                        options = quizState.quizQuestion?.options,
                        onSelectAnswer = {
                            onSelectAnswer(it)
                        })
                    Spacer(modifier = Modifier.weight(1f))
                    QuizButton(
                        text = "Next",
                        addIcon = true,
                        onClick = {
                            // Handle next button click
                        }
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProgressIndicator(
                    progress = 0.2f,
                    questionNumber = quizState.questionNumber,
                    totalQuestion = quizState.totalQuestions
                )
            }
        }
    }
}