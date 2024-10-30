package com.example.missingpiece

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import com.example.missingpiece.ui.theme.Blue10


enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

@Composable
fun Game(viewModel: GameViewModel, onBackPressed: () -> Unit) {
    val imageBitmap = ImageBitmap.imageResource(R.drawable.puppies)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        val halfScreenHeight = maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier
                .height(halfScreenHeight)
                .fillMaxWidth(fraction = 1f)
                .pointerInput(Unit) {
                }
            ) {
                DrawPuzzleBoard(halfScreenHeight, viewModel, onBackPressed)
            }
            Text(text = "Game", color = Blue10)
        }
    }
}

@Composable
fun DrawPuzzleBoard(screenHeight: Dp, viewModel: GameViewModel, onBackPressed: () -> Unit) {
    val puzzle = remember { Puzzle() }
    var grid = remember { mutableStateOf(viewModel.getSavedGameState() ?: puzzle.generateGrid()) }
    var emptyPosition = remember { mutableStateOf(puzzle.findEmptyPosition(grid.value)) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.saveGameState(grid.value)
        }
    }

    BackHandler {
        viewModel.saveGameState(grid.value)
        onBackPressed()
    }

    Canvas(modifier = Modifier
        .size(screenHeight)
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
                                size.width / 3f
                            )
                        if (touchedBox != null) {
                            with(puzzle) {
                                val (newGrid, newEmptyPosition) = grid.value.tryMove(
                                    direction,
                                    emptyPosition.value,
                                    touchedBox
                                )
                                grid.value = newGrid
                                emptyPosition.value = newEmptyPosition
                            }
                        }
                    }
                }
            )
        }) {
        val cellSize = size.width / 3

        drawLine(Color.White, Offset(cellSize, 0f), Offset(cellSize, size.height), strokeWidth = 2f)
        drawLine(
            Color.White,
            Offset(cellSize * 2, 0f),
            Offset(cellSize * 2, size.height),
            strokeWidth = 2f
        )
        drawLine(Color.White, Offset(0f, cellSize), Offset(size.width, cellSize), strokeWidth = 2f)
        drawLine(
            Color.White,
            Offset(0f, cellSize * 2),
            Offset(size.width, cellSize * 2),
            strokeWidth = 2f
        )

        grid.value.forEachIndexed { y, row ->
            row.forEachIndexed { x, number ->
                Log.i("HI", y.toString() + x.toString())
                if (number != 0) {
                    with(puzzle) {
                        drawBoxWithNumber(number, x, y, cellSize)
                    }
                }
            }
        }
    }
}

