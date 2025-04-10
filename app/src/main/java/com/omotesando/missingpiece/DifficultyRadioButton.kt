package com.omotesando.missingpiece

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omotesando.missingpiece.ui.theme.Orange10

@Composable
fun DifficultySelector(viewModel: GameViewModel) {
    val difficulties = listOf(3, 4, 5)
    val selectedDifficulty by viewModel.difficulty

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.select_difficulty),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            difficulties.forEach { difficulty ->
                CustomButton(
                    text = "${difficulty}",
                    onClick = {
                        viewModel.setDifficulty(difficulty)
                        viewModel.clearSavedGame()
                        viewModel.resetGame()
                        viewModel.completeReset()
                    },
                    newWidth = 60,
                    newHeight = 45,
                    newFontSize = 16,
                    borderColor = if (selectedDifficulty == difficulty) Color.White else Orange10
                )
            }
        }
    }
}