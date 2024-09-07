package com.example.quizapp.ui.compose.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ScoreGaugeWidget(score: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(2f)
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val centerX = width / 2f
            val centerY = height
            val radius = height - 10.dp.toPx()
            val strokeWidth = 8.dp.toPx()

            // Draw static gradient arc
            val brush = Brush.sweepGradient(
                0.0f to Color(0xFFFF4500),  // Red-Orange
                0.5f to Color(0xFFFFFF00),  // Yellow
                1.0f to Color(0xFF32CD32)   // Lime Green
            )
            drawArc(
                brush = brush,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Draw arrow
            val arrowAngle = (180 + 180 * (score / 100f)) * (PI / 180f).toFloat()
            val arrowX = centerX + cos(arrowAngle) * (radius - strokeWidth / 2)
            val arrowY = centerY + sin(arrowAngle) * (radius - strokeWidth / 2)

            val arrowPath = Path().apply {
                moveTo(arrowX, arrowY - 12.dp.toPx())
                lineTo(arrowX - 6.dp.toPx(), arrowY + 4.dp.toPx())
                lineTo(arrowX + 6.dp.toPx(), arrowY + 4.dp.toPx())
                close()
            }
            drawPath(path = arrowPath, color = Color.DarkGray)
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$score%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }
    }
}