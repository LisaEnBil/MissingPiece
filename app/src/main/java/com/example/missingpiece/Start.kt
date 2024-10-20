package com.example.missingpiece

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.missingpiece.ui.theme.Blue10
import com.example.missingpiece.ui.theme.Blue40
import com.example.missingpiece.ui.theme.MissingPieceTheme
import com.example.missingpiece.ui.theme.Orange40

@Composable
fun Start(
    goToGame : () -> Unit,
    goToHighScore : () -> Unit,
    goToInstructions: () -> Unit,
    modifier: Modifier = Modifier){
    Column(
        modifier = Modifier.fillMaxSize().background(color = Blue10),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ){
        Button(
            onClick = { goToGame()},
            colors = ButtonDefaults
                .buttonColors(containerColor = Blue40),
            border = BorderStroke(2.dp, color = Orange40),
            modifier = Modifier
                .width(width = 250.dp)
            ) {
            Text("START GAME")
        }

        Button(
            onClick = { goToHighScore()},
            colors = ButtonDefaults
                .buttonColors(containerColor = Blue40),
            border = BorderStroke(2.dp, color = Orange40),
            modifier = Modifier
                .width(width = 250.dp)

        ) {
            Text("HIGH SCORE")
        }

        Button(
            onClick = { goToInstructions() },
            colors = ButtonDefaults
                .buttonColors(containerColor = Blue40),
            border = BorderStroke(2.dp, color = Orange40),
            modifier = Modifier
                .width(width = 250.dp)
            ) {
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