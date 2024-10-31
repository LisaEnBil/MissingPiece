package com.example.missingpiece

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _difficulty = mutableIntStateOf(3)
    val difficulty: State<Int> = _difficulty

    private val _hasOngoingGame = mutableStateOf(false)

    val hasOngoingGame: State<Boolean> = _hasOngoingGame

    private var savedGameState: List<List<Int>>? = null

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
