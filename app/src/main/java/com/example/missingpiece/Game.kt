package com.example.missingpiece

import android.R.attr.left
import android.R.attr.top
import android.R.color
import android.util.Log
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.missingpiece.ui.theme.Blue10
import com.example.missingpiece.ui.theme.MissingPieceTheme
import kotlin.math.abs


enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

fun getDragDirection(dragAmount: Offset): Direction? {
    return when {
        abs(dragAmount.x) > abs(dragAmount.y) -> {
            if (dragAmount.x > 0) Direction.RIGHT else Direction.LEFT
        }
        abs(dragAmount.y) > abs(dragAmount.x) -> {
            if (dragAmount.y > 0) Direction.DOWN else Direction.UP
        }
        else -> null
    }
}


fun findEmptyPosition(grid: List<List<Int>>): Pair<Int, Int> {
    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, number ->
            if (number == 0) return x to y
        }
    }
    throw IllegalStateException("No empty spaces in grid")
}



@Composable
fun Game(){
    val imageBitmap = ImageBitmap.imageResource(R.drawable.puppies)

    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        val halfScreenHeight = maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Box(modifier = Modifier
                .height(halfScreenHeight)
                .fillMaxWidth(fraction = 1f)
                .pointerInput(Unit) {

                }
            ){

            }
            drawPuzzleBoard(halfScreenHeight)
            Text(text = "Game", color = Blue10)
        }
    }
}

fun generateGrid(): List<List<Int>> {
    return (0..8).shuffled().chunked(3)
}

@Composable
fun drawPuzzleBoard(screenHeight: Dp) {

    var grid = remember { mutableStateOf(generateGrid()) }
    var emptyPosition = remember { mutableStateOf(findEmptyPosition(grid.value)) }


    Canvas(modifier = Modifier
        .size(screenHeight)
        .pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    // When drag ends, stop further detection until next swipe
                },
                onDragCancel = {
                    // When drag cancels, stop further detection until next swipe
                },
                onDrag = { change, dragAmount ->
                    // Determine the direction of the swipe based on the drag amount.
                    val direction = getDragDirection(dragAmount)
                    if (direction != null) {
                        // Identify which box was touched.
                        val touchedBox =
                            findTouchedBox(change.position, grid.value.size, size.width / 3f)
                        if (touchedBox != null) {
                            // Move the box only if it is adjacent to the empty space.
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
            )
        }) {
        val cellSize = size.width / 3

        drawLine(Color.White, Offset(cellSize, 0f), Offset(cellSize, size.height), strokeWidth = 2f)
        drawLine(Color.White, Offset(cellSize * 2, 0f), Offset(cellSize * 2, size.height), strokeWidth = 2f)
        drawLine(Color.White, Offset(0f, cellSize), Offset(size.width, cellSize), strokeWidth = 2f)
        drawLine(Color.White, Offset(0f, cellSize * 2), Offset(size.width, cellSize * 2), strokeWidth = 2f)

        grid.value.forEachIndexed { y, row ->
            row.forEachIndexed { x, number ->
                Log.i("HI", y.toString() + x.toString())
                if (number != 0) {
                    drawBoxWithNumber( number, x, y, cellSize)
                }
            }
        }

    }

}

fun DrawScope.drawBoxWithNumber(number: Int, x: Int, y: Int, cellSize: Float){

    val padding = 5
    val boxSize = cellSize - (padding * 2)
    val left = (x * cellSize) + padding
    val top = (y * cellSize) + padding


    drawRect(
        color = Color.Blue,
        topLeft = Offset(left, top),
        size = Size(boxSize, boxSize)
    )

    drawIntoCanvas { canvas ->
        val paint = Paint().asFrameworkPaint().apply {
            color = android.graphics.Color.BLUE
            textSize = 24.sp.toPx()
            textAlign = android.graphics.Paint.Align.CENTER
        }
        canvas.nativeCanvas.drawText(
            number.toString(),
            left + (cellSize / 2),
            top + (cellSize / 2) + (paint.textSize / 3),
            paint
        )
    }
}

fun List<List<Int>>.tryMove(
    direction: Direction,
    emptyPosition: Pair<Int, Int>,
    touchedBox: Pair<Int, Int>
): Pair<List<List<Int>>, Pair<Int, Int>> {
    val (emptyX, emptyY) = emptyPosition
    val (touchedX, touchedY) = touchedBox
    val newGrid = this.map { it.toMutableList() }

    return when (direction) {
        Direction.UP -> if (touchedX == emptyX && touchedY == emptyY + 1) {
            // Move the box down if the empty space is directly below it.
            newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
            newGrid[touchedY][touchedX] = 0
            newGrid to (touchedX to touchedY)
        } else this to emptyPosition

        Direction.DOWN -> if (touchedX == emptyX && touchedY == emptyY - 1) {
            // Move the box up if the empty space is directly above it.
            newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
            newGrid[touchedY][touchedX] = 0
            newGrid to (touchedX to touchedY)
        } else this to emptyPosition

        Direction.LEFT -> if (touchedY == emptyY && touchedX == emptyX + 1) {
            // Move the box left if the empty space is directly to the left of it.
            newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
            newGrid[touchedY][touchedX] = 0
            newGrid to (touchedX to touchedY)
        } else this to emptyPosition

        Direction.RIGHT -> if (touchedY == emptyY && touchedX == emptyX - 1) {
            // Move the box right if the empty space is directly to the right of it.
            newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
            newGrid[touchedY][touchedX] = 0
            newGrid to (touchedX to touchedY)
        } else this to emptyPosition
    }
}


fun findTouchedBox(position: Offset, gridSize: Int, cellSize: Float): Pair<Int, Int>? {
    val x = (position.x / cellSize).toInt()
    val y = (position.y / cellSize).toInt()

    return if (x in 0 until gridSize && y in 0 until gridSize) x to y else null

}




@Preview(showBackground = true)
@Composable
fun GamePreview() {
    MissingPieceTheme {
        Game()
    }
}