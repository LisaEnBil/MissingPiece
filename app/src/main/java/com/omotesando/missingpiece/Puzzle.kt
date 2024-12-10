package com.omotesando.missingpiece

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp
import com.omotesando.missingpiece.ui.theme.Orange10
import com.omotesando.missingpiece.ui.theme.Orange20
import com.omotesando.missingpiece.ui.theme.Orange40
import com.omotesando.missingpiece.ui.theme.Orange60
import com.omotesando.missingpiece.ui.theme.Yellow10
import kotlin.math.abs

class Puzzle {

    private fun countInversions(flatGrid: List<Int>): Int {
        var inversions = 0
        for (i in flatGrid.indices) {
            for (j in i + 1 until flatGrid.size) {
                if (flatGrid[i] > flatGrid[j] && flatGrid[i] != 0 && flatGrid[j] != 0) {
                    inversions++
                }
            }
        }
        return inversions
    }

    private fun isSolvable(grid: List<List<Int>>): Boolean {
        val flatGrid = grid.flatten()
        val inversions = countInversions(flatGrid)
        val emptyRowFromBottom = grid.size - findEmptyPosition(grid).second

        if (grid.size % 2 == 1) {

            return inversions % 2 == 0
        }
        return (inversions % 2 == 1) == (emptyRowFromBottom % 2 == 0)
    }

    private fun isPuzzleSolved(currentState: List<List<Int>>, difficulty: Int): Boolean {

        val goalStateList = (1 until difficulty * difficulty).toList() + 0
        val goalState = goalStateList.chunked(difficulty)

        if (currentState == goalState) {
            println("Congratulations! Puzzle solved!")
        } else {
            println("Keep trying!")
        }
        return currentState == goalState
    }

    fun generateGrid(difficulty: Int): List<List<Int>> {
        var grid: List<List<Int>>
        val gridUpperValue = difficulty * difficulty -1
        do {
            grid = (0..gridUpperValue).shuffled().chunked(difficulty)
        } while (!isSolvable(grid))
        return grid
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
        viewModel: GameViewModel,
        difficulty: Int,
        emptyPosition: Pair<Int, Int>,
        touchedBox: Pair<Int, Int>,
        onSuccessfulMove: () -> Unit,
    ): Pair<List<List<Int>>, Pair<Int, Int>> {
        val (emptyX, emptyY) = emptyPosition
        val (touchedX, touchedY) = touchedBox
        val newGrid = this.map { it.toMutableList() }


        return when (direction) {
            Direction.UP -> if (touchedX == emptyX && touchedY == emptyY + 1) {
                newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
                newGrid[touchedY][touchedX] = 0
                onSuccessfulMove()
                val g = isPuzzleSolved(newGrid, difficulty)
                if (g){
                    viewModel.hasFinishedPuzzle()
                }
                newGrid to (touchedX to touchedY)
            } else this to emptyPosition

            Direction.DOWN -> if (touchedX == emptyX && touchedY == emptyY - 1) {
                newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
                newGrid[touchedY][touchedX] = 0
                onSuccessfulMove()
                val g = isPuzzleSolved(newGrid, difficulty)
                if (g){
                    viewModel.hasFinishedPuzzle()
                }
                newGrid to (touchedX to touchedY)
            } else this to emptyPosition

            Direction.LEFT -> if (touchedY == emptyY && touchedX == emptyX + 1) {
                newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
                newGrid[touchedY][touchedX] = 0
                onSuccessfulMove()
               isPuzzleSolved(newGrid, difficulty)
                if (isPuzzleSolved(newGrid, difficulty)){
                    viewModel.hasFinishedPuzzle()
                }
                newGrid to (touchedX to touchedY)
            } else this to emptyPosition

            Direction.RIGHT -> if (touchedY == emptyY && touchedX == emptyX - 1) {
                newGrid[emptyY][emptyX] = newGrid[touchedY][touchedX]
                newGrid[touchedY][touchedX] = 0
                onSuccessfulMove()
                val g = isPuzzleSolved(newGrid, difficulty)
                if (g){
                    viewModel.hasFinishedPuzzle()
                }
                newGrid to (touchedX to touchedY)

            } else this to emptyPosition
        }
    }

    /*fun DrawScope.drawBoxWithNumber(number: Int, x: Int, y: Int, cellSize: Float) {

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
                textSize = 36.sp.toPx()
                textAlign = android.graphics.Paint.Align.CENTER
            }
            canvas.nativeCanvas.drawText(
                number.toString(),
                left + (cellSize / 2),
                top + (cellSize / 2) + (paint.textSize / 3),
                paint
            )
        }
    } */
    fun DrawScope.drawBoxWithNumber(number: Int, x: Int, y: Int, cellSize: Float, difficulty: Int) {
        val padding = 5
        val boxSize = cellSize - (padding * 2)
        val left = (x * cellSize) + padding
        val top = (y * cellSize) + padding

        // Determine the color based on the number and game size
        val color1 = when (difficulty) {
            3 -> when (number) {
                in 1..3 -> Yellow10
                in 4..6 -> Orange60
                else -> Orange40
            }
            4 -> when (number) {
                in 1..4 -> Yellow10
                in 5..8 -> Orange60
                in 9..12 -> Orange40
                else -> Orange10
            }
            else -> when (number) {
                in 1..5 -> Yellow10
                in 6..10 -> Orange60
                in 11..15 -> Orange40
                in 16..20 -> Orange10
                else -> Orange20
            }
        }

        drawRect(
            color = color1,
            topLeft = Offset(left, top),
            size = Size(boxSize, boxSize)
        )

        drawIntoCanvas { canvas ->
            val paint = Paint().asFrameworkPaint().apply {
                color = android.graphics.Color.WHITE
                textSize = 36.sp.toPx()
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

}


