package com.example.missingpiece

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat.FocusDirection


@Composable
fun PuzzleCompletedDialog(
    viewModel: GameViewModel,
    hsViewModel: HighScoreViewModel,
    goToStart: () -> Unit,
    score: Int,
    difficulty: Int
) {
    var showDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val newScore = calculatePoints(difficulty, score)

    LaunchedEffect(Unit) {
        showDialog = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        AnimatedContent(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            targetState = showDialog, label = "",
        ) { targetState: Boolean ->
            if (targetState) {
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),

                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.puzzle_completed),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            stringResource(R.string.puzzle_solved_sentence),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))


                        Text(
                            "${stringResource(R.string.score)}: " + newScore.toString(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        TextField(
                            value = text,
                            onValueChange = { newText ->
                                if (newText.length <= 20) {
                                    text = newText
                                }
                            },
                            modifier = Modifier
                                .onKeyEvent { keyEvent ->
                                    if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                        true
                                    } else {
                                        false
                                    }
                                },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomButton(
                            text = stringResource(R.string.start),
                            modifier = Modifier.padding(5.dp),
                            onClick = {
                                hsViewModel.addHighScore(text, newScore, difficulty)
                                viewModel.resetGame()
                                viewModel.clearSavedGame()
                                viewModel.completeReset()
                                goToStart()
                            },
                            newWidth = 120,
                            newHeight = 65,
                            newFontSize = 18
                        )
                    }
                }
            }
        }
    }
}

fun calculatePoints(difficulty: Int, moves: Int): Int {
    val basePoints = when (difficulty) {
        2 -> 500
        3 -> 1000
        4 -> 2000
        5 -> 3000
        else -> 0
    }

    val minMoves = (difficulty * difficulty) - 1
    val movePenalty = maxOf(0, moves - minMoves) * 10

    return maxOf(0, basePoints - movePenalty)
}

