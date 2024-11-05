package com.example.missingpiece

import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.missingpiece.ui.theme.Blue10
import com.example.missingpiece.ui.theme.Blue40
import com.example.missingpiece.ui.theme.MissingPieceTheme
import com.example.missingpiece.ui.theme.Orange10
import com.example.missingpiece.ui.theme.Orange40


@Composable
fun Settings(viewModel: GameViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Blue40),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Difficulty levels",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.White)

        val difficulty = viewModel.difficulty.value
        val radioOptions = listOf("3X3", "4X4", "5X5")
        val gridSize1 = when (difficulty) {
            3 -> "3X3"
            4 -> "4X4"
            5 -> "5X5"
            else -> "3X3"
        }

        val (selectedOption, onOptionSelected) = remember { mutableStateOf(gridSize1) }
        Row(
            modifier = Modifier
                .padding(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment =  Alignment.CenterVertically

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
                    verticalAlignment =  Alignment.CenterVertically

                    ) {
                    RadioButton(
                        selected = (level == selectedOption),
                        onClick = {
                            val gridSize = when (level) {
                                "3X3" -> 3
                                "4X4" -> 4
                                "5X5" -> 5
                                else -> 3
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

        Text(text = "INSTRUCTIONS", fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.White)
        Text(text = "Intstructions on how to play game, play game by moving one brick at a time. Move the number into numerical fashion.",  fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.White)
        Spacer(Modifier.weight(1f))
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    MissingPieceTheme {
        Settings()
    }
}