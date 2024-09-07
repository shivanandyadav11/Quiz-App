package com.example.quizapp.ui.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleWithText(
    text: String,
    secondaryText: String? = null,
    circleSize: Dp = 150.dp,
    circleColor: Color = Color.White,
    textColor: Color = Color.Black,
    textSize: Int = 24,
    color: Color = Color.Red
) {
    Box(
        modifier = Modifier
            .size(circleSize)
            .clip(CircleShape)
            .background(circleColor),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = text,
                style = TextStyle(
                    color = textColor,
                    fontSize = textSize.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = color
            )
            secondaryText?.let {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = it,
                        color = Color.Black
                    )
                }
            }
        }
    }
}