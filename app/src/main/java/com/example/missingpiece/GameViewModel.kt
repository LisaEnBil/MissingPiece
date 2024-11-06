package com.example.missingpiece

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {



    private val _difficulty = mutableIntStateOf(3)
    val difficulty: State<Int> = _difficulty

    private val _score = mutableIntStateOf(0)
    val score: State<Int> = _score

    private val _hasOngoingGame = mutableStateOf(false)

    val hasOngoingGame: State<Boolean> = _hasOngoingGame

    private var savedGameState: List<List<Int>>? = null

    fun setHighScore() {
        _score.intValue += 1
    }

    fun resetHighScore() {
        _score.intValue = 0
    }

    fun getCurrentScore(): Int = _score.intValue

    fun setDifficulty(gridSize: Int) {
        _difficulty.intValue = gridSize
    }

    fun saveGameState(gameState: List<List<Int>>) {
        savedGameState = gameState
        _hasOngoingGame.value = true
    }

    fun getSavedGameState(): List<List<Int>>? = savedGameState

    fun clearSavedGame() {

        _hasOngoingGame.value = false
        savedGameState = null
    }


}
