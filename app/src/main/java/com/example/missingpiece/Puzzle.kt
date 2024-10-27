package com.example.missingpiece

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp
import com.example.missingpiece.ui.theme.Blue10
import kotlin.math.abs

class Puzzle {

    fun generateGrid(): List<List<Int>> {
        return (0..8).shuffled().chunked(3)
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

    fun findTouchedBox(position: Offset, gridSize: Int, cellSize: Float): Pair<Int, Int>? {
        val x = (position.x / cellSize).toInt()
        val y = (position.y / cellSize).toInt()

        return if (x in 0 until gridSize && y in 0 until gridSize) x to y else null
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
}



fun DrawScope.drawBoxWithNumber(number: Int, x: Int, y: Int, cellSize: Float){

    val padding = 5
    val boxSize = cellSize - (padding * 2)
    val left = (x * cellSize) + padding
    val top = (y * cellSize) + padding

    drawRect(
        color = Blue10,
        topLeft = Offset(left, top),
        size = Size(boxSize, boxSize)
    )

    drawIntoCanvas { canvas ->
        val paint = Paint().asFrameworkPaint().apply {
            color = android.graphics.Color.WHITE
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