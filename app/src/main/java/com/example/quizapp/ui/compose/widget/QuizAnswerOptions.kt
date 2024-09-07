package com.example.quizapp.ui.compose.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizapp.model.Option
import com.example.quizapp.ui.theme.DarkGreen
import com.example.quizapp.ui.theme.DarkGrey
import com.example.quizapp.ui.theme.LightGrey

@Composable
fun AnswerOptions(options: List<Option?>?, onSelectAnswer: (String) -> Unit) {
    options?.forEach { option ->
        option?.let {
            val modifier = if (it.selected)
                Modifier
                    .fillMaxWidth()
                    .border(
                        2.dp,
                        DarkGreen,
                        shape = CardDefaults.shape
                    ) else Modifier.fillMaxWidth()
            Card(
                modifier = modifier,
                colors = CardDefaults.cardColors(containerColor = LightGrey)
            ) {
                Row(modifier = Modifier.padding(vertical = 20.dp)) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = if(it.selected) Icons.Default.CheckCircle else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if(it.selected) DarkGreen else DarkGrey,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = it.text)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
