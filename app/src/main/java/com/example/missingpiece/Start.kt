package com.example.missingpiece

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
) {
    val isResetComplete by viewModel.isResetComplete
    val hasOngoingGame by viewModel.hasOngoingGame
    val isShowingDifficultyLevels by viewModel.isShowingDifficultyLevels

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            if (hasOngoingGame && isResetComplete) {
                CustomButton(
                    text = "RESUME GAME",
                    onClick = { goToGame() },
                    newWidth = 250,
                    newHeight = 65,
                    newFontSize = 28
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 25.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    text = "START GAME",
                    onClick = {
                        viewModel.clearSavedGame()
                        viewModel.resetHighScore()
                        goToGame()
                    },
                    newWidth = 250,
                    newHeight = 65,
                    newFontSize = 28
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 25.dp),
                contentAlignment = Alignment.Center
            ) {

                CustomButton(
                    text = "HIGH SCORE",
                    onClick = { goToHighScore() },
                    newWidth = 250,
                    newHeight = 65,
                    newFontSize = 28
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 25.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    text = "DIFFICULTY",
                    onClick = { viewModel.setIsShowingDifficultyLevels() },
                    newWidth = 250,
                    newHeight = 65,
                    newFontSize = 28
                )
            }
            Box(   modifier = Modifier.height(100.dp),
                contentAlignment = Alignment.Center) {

                if (isShowingDifficultyLevels) {

                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.TopCenter
                    ) {
                        DifficultySelector(viewModel)
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}
