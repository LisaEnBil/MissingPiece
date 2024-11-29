package com.example.missingpiece

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.missingpiece.ui.theme.Orange10

@Composable
fun DifficultySelector(viewModel: GameViewModel) {
    val difficulties = listOf(2, 3, 4, 5)
    val selectedDifficulty by viewModel.difficulty

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "Select Difficulty",
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
                    text = "${difficulty}x$difficulty",
                    onClick = {
                        viewModel.setDifficulty(difficulty)
                        viewModel.clearSavedGame()
                    },
                    newWidth = 60,
                    newFontSize = 16,
                    borderColor = if (selectedDifficulty == difficulty) Color.White else Orange10
                )
            }
        }
    }
}


@Composable
fun DifficultyRadioButton(viewModel: GameViewModel) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Difficulty levels",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.White
        )

        val difficulty = viewModel.difficulty.value
        val radioOptions = listOf("2X2", "3X3", "4X4", "5X5")
        val gridSize1 = when (difficulty) {
            2 -> "2X2"
            3 -> "3X3"
            4 -> "4X4"
            5 -> "5X5"
            else -> "2X2"
        }

        val (selectedOption, onOptionSelected) = remember { mutableStateOf(gridSize1) }
        Row(
            modifier = Modifier
                .padding(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            radioOptions.forEach { level ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = (level == selectedOption),
                            onClick = {
                                onOptionSelected(level)
                            }
                        )
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    RadioButton(
                        selected = (level == selectedOption),
                        onClick = {
                            val gridSize = when (level) {
                                "2X2" -> 2
                                "3X3" -> 3
                                "4X4" -> 4
                                "5X5" -> 5
                                else -> 2
                            }
                            onOptionSelected(level)
                            viewModel.setDifficulty(gridSize)
                            viewModel.clearSavedGame()
                        }
                    )
                    Text(
                        text = level,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }

    }
}
