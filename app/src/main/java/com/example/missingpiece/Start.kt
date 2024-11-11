package com.example.missingpiece

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val isResetComplete by viewModel.isResetComplete
    val hasOngoingGame by viewModel.hasOngoingGame

    Log.i("Start", isResetComplete.toString())

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

            if (hasOngoingGame && isResetComplete) {

                CustomButton(
                    text = "RESUME GAME",
                    onClick = {
                        goToGame()
                    }
                )
            }

            CustomButton(
                text = "START GAME",
                onClick = {

                    viewModel.clearSavedGame()
                    viewModel.resetHighScore()
                    goToGame()
                }
            )

            CustomButton(
                text = "HIGH SCORE",
                onClick = {
                    goToHighScore()
                }
            )

            CustomButton(
                text = "SETTINGS",
                onClick = {
                    goToSettings()
                }
            )
        }
    }
}


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    borderColor: Color = Orange10,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Blue10),
        border = BorderStroke(2.dp, color = borderColor),
        modifier = modifier
            .width(250.dp)
            .height(45.dp)
            .padding(bottom = 5.dp)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
    }
}

