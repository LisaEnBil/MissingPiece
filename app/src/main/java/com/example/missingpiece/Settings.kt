package com.example.missingpiece

import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.missingpiece.ui.theme.Blue10
import com.example.missingpiece.ui.theme.MissingPieceTheme


@Composable
fun Settings(onDifficultySelected: (Int) -> Unit, viewModel: GameViewModel,) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Difficulty levels", color = Blue10)

        val radioOptions = listOf("3X3", "4X4", "5X5")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

        Column {
            radioOptions.forEach { level ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (level == selectedOption),
                            onClick = {
                                onOptionSelected(level)

                             /*   val gridSize = when (level) {
                                    "3X3" -> 3
                                    "4X4" -> 4
                                    "5X5" -> 5
                                    else -> 3
                                }
                                viewModel.setDifficulty(level.toInt()) */
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (level == selectedOption),
                        onClick = { onOptionSelected(level)
                            onOptionSelected(level)

                            val gridSize = when (level) {
                                "3X3" -> 3
                                "4X4" -> 4
                                "5X5" -> 5
                                else -> 3
                            }

                            Log.i("HI", gridSize.toString())
                            viewModel.setDifficulty(gridSize)
                        }
                    )
                    Text(
                        text = level,
                        style = MaterialTheme.typography.bodySmall.merge(),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    MissingPieceTheme {
        Settings()
    }
}