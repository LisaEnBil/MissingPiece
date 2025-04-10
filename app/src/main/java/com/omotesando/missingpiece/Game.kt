package com.omotesando.missingpiece

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omotesando.missingpiece.ui.theme.Blue10
import com.omotesando.missingpiece.ui.theme.Green10


enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

@Composable
fun Game(
    viewModel: GameViewModel,
    hsViewModel: HighScoreViewModel,
    onBackPressed: () -> Unit,
    goToStart: () -> Unit,
) {
    val imageBitmap = ImageBitmap.imageResource(R.drawable.puppies)

    val difficulty = viewModel.difficulty.value
    val score = viewModel.score.value

    val colorStops = arrayOf(
        0.0f to Blue10,
        1f to Green10
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = colorStops))
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight
        val isLandscape = screenWidth > screenHeight

        if (isLandscape) {
            Row {
                if (viewModel.hasFinishedGame.value) {
                    PuzzleCompletedDialog(viewModel, hsViewModel, goToStart, score, difficulty)
                }
                Box(
                    modifier = Modifier.weight(0.7f),
                    contentAlignment = Alignment.Center
                ) {

                    DrawPuzzleBoard(screenHeight, viewModel, onBackPressed, difficulty)
                }
                Column(modifier = Modifier.weight(0.3f)) {
                    Text(
                        text = "${stringResource(R.string.moves)}: $score", color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (viewModel.hasFinishedGame.value) {
                    PuzzleCompletedDialog(viewModel, hsViewModel, goToStart, score, difficulty)
                }
                Box(modifier = Modifier
                    .height(screenWidth)
                    .fillMaxWidth(fraction = 1f)
                    .pointerInput(Unit) {
                    }
                ) {
                    DrawPuzzleBoard(screenWidth, viewModel, onBackPressed, difficulty)
                }
                Text(
                    text = "${stringResource(R.string.moves)}: $score", color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                )
            }
        }
    }
}


@Composable
fun DrawPuzzleBoard(
    screenHeight: Dp,
    viewModel: GameViewModel,
    onBackPressed: () -> Unit,
    difficulty: Int
) {
    val puzzle = remember { Puzzle() }

    val grid = remember {
        mutableStateOf(viewModel.getSavedGameState() ?: puzzle.generateGrid(difficulty))
    }

    val emptyPosition = remember { mutableStateOf(puzzle.findEmptyPosition(grid.value)) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.saveGameState(grid.value)
        }
    }

    BackHandler {
        viewModel.saveGameState(grid.value)
        onBackPressed()
    }
    val difficultyInt = difficulty * 1f

    Canvas(
        modifier = Modifier
            .padding(10.dp)
            .size(screenHeight - 20.dp)
            .border(2.dp, Color.Black)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {},
                    onDragCancel = {},
                    onDrag = { change, dragAmount ->
                        val direction = puzzle.getDragDirection(dragAmount)
                        if (direction != null) {
                            val touchedBox =
                                puzzle.findTouchedBox(
                                    change.position,
                                    grid.value.size,
                                    size.width / difficultyInt
                                )
                            if (touchedBox != null) {
                                with(puzzle) {

                                    val (newGrid, newEmptyPosition) = grid.value.tryMove(
                                        direction,
                                        viewModel,
                                        difficulty,
                                        emptyPosition.value,
                                        touchedBox,

                                        ) {

                                        viewModel.setHighScore()
                                    }
                                    grid.value = newGrid
                                    emptyPosition.value = newEmptyPosition
                                }
                            }
                        }
                    }
                )
            }) {
        val screenWidth = size.width
        val newScreen = screenHeight.toPx() * 1f
        val isLandscape = screenWidth > newScreen

        grid.value.forEachIndexed { y, row ->
            row.forEachIndexed { x, number ->

                if (number != 0) {
                    with(puzzle) {
                        if (isLandscape) {
                            val cellSize = newScreen / difficulty
                            drawBoxWithNumber(number, x, y, cellSize, difficulty)
                        } else {
                            val cellSize = size.width / difficulty
                            drawBoxWithNumber(number, x, y, cellSize, difficulty)
                        }

                    }
                }
            }
        }
    }
}
