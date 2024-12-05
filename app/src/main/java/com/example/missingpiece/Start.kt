package com.example.missingpiece

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun Start(
    viewModel: GameViewModel,
    goToGame: () -> Unit,
    goToHighScore: () -> Unit,
) {
    val isResetComplete by viewModel.isResetComplete
    val hasOngoingGame by viewModel.hasOngoingGame
    val isShowingDifficultyLevels by viewModel.isShowingDifficultyLevels

    val configuration = LocalConfiguration.current
    val isLandscape = remember(configuration) {
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (isLandscape) {
            LandscapeLayout(
                viewModel,
                goToGame,
                goToHighScore,
                hasOngoingGame,
                isResetComplete,
                isShowingDifficultyLevels
            )
        } else {
            PortraitLayout(
                viewModel,
                goToGame,
                goToHighScore,
                hasOngoingGame,
                isResetComplete,
                isShowingDifficultyLevels
            )
        }
    }
}


@Composable
fun PortraitLayout(
    viewModel: GameViewModel,
    goToGame: () -> Unit,
    goToHighScore: () -> Unit,
    hasOngoingGame: Boolean,
    isResetComplete: Boolean,
    isShowingDifficultyLevels: Boolean
) {

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


@Composable
fun LandscapeLayout(
    viewModel: GameViewModel,
    goToGame: () -> Unit,
    goToHighScore: () -> Unit,
    hasOngoingGame: Boolean,
    isResetComplete: Boolean,
    isShowingDifficultyLevels: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            if (hasOngoingGame && isResetComplete) {
                CustomButton(
                    text = "RESUME GAME",
                    onClick = { goToGame() },
                    newWidth = 200,
                    newHeight = 45,
                    newFontSize = 20,
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else{
                Box(modifier = Modifier.height(61.dp))
            }

            CustomButton(
                text = "START GAME",
                onClick = {
                    viewModel.clearSavedGame()
                    viewModel.resetHighScore()
                    goToGame()
                },
                newWidth = 200,
                newHeight = 45,
                newFontSize = 20
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CustomButton(
                text = "HIGH SCORE",
                onClick = { goToHighScore() },
                newWidth = 200,
                newHeight = 45,
                newFontSize = 20
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                text = "DIFFICULTY",
                onClick = { viewModel.setIsShowingDifficultyLevels() },
                newWidth = 200,
                newHeight = 45,
                newFontSize = 20
            )
        }
    }
    if (isShowingDifficultyLevels) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            DifficultySelector(viewModel)
        }
    }
}