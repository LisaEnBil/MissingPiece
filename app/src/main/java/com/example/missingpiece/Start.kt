package com.example.missingpiece

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.missingpiece.ui.theme.MissingPieceTheme

@Composable
fun Start(
    goToGame : () -> Unit,
    goToHighScore : () -> Unit,
    goToInstructions: () -> Unit){
    Column {
        Button(onClick = {
            goToGame()
        }) {
            Text("START GAME")
        }

        Button(onClick = {
            goToHighScore()
        }) {
            Text("HIGH SCORE")
        }

        Button(onClick = {
            goToInstructions()
        }) {
            Text("INSTRUCTIONS")
        }

    }
}



@Preview(showBackground = true)
@Composable
fun StartPreview() {
    MissingPieceTheme {
        Start(
            goToGame = {},
            goToHighScore = {},
            goToInstructions = {}
        )
    }
}