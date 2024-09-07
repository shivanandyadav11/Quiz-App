package com.example.quizapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.compose.widget.CircleWithText
import com.example.quizapp.ui.compose.widget.QuizButton
import com.example.quizapp.ui.viewModel.QuizViewModel

@Composable
internal fun QuizLandingScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    gradientStartColor: Color = Color.Blue,
    gradientEndColor: Color = Color.Transparent,
    onStartClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientEndColor,
                        gradientStartColor.copy(alpha = 0.4f)
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(1f))

            CircleWithText(
                text = "Quiz",
                circleSize = 200.dp,
                circleColor = Color.White,
                textColor = Color.Black,
                textSize = 48
            )
            Spacer(modifier = Modifier.weight(1f))

            QuizButton(
                text = "Start",
                onClick = onStartClick,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}