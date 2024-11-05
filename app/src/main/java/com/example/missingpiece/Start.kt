package com.example.missingpiece

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.missingpiece.ui.theme.Blue10
import com.example.missingpiece.ui.theme.Orange10


@Composable
fun Start(
    viewModel: GameViewModel,
    goToGame: () -> Unit,
    goToHighScore: () -> Unit,
    goToSettings: () -> Unit,
) {
    val radioOptions = listOf("3X3", "4X4", "5X5")

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (viewModel.hasOngoingGame.value) {
                Button(
                    onClick = {
                        goToGame()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue10),
                    border = BorderStroke(2.dp, color = Orange10),
                    modifier = Modifier.width(width = 250.dp)
                ) {

                    Text("RESUME GAME")
                }
            }

            Button(
                onClick = {
                    viewModel.clearSavedGame()
                    goToGame()
                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = Blue10),
                border = BorderStroke(2.dp, color = Orange10),
                modifier = Modifier
                    .width(width = 250.dp)
            ) {
                Text(
                    "START GAME",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { goToHighScore() },
                colors = ButtonDefaults
                    .buttonColors(containerColor = Blue10),
                border = BorderStroke(2.dp, color = Orange10),
                modifier = Modifier
                    .width(width = 250.dp)
            ) {
                Text(
                    "HIGH SCORE",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { goToSettings() },
                colors = ButtonDefaults
                    .buttonColors(containerColor = Blue10),
                border = BorderStroke(2.dp, color = Orange10),
                modifier = Modifier
                    .width(width = 250.dp)
            ) {
                Text(
                    "SETTINGS",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }

    }

}




