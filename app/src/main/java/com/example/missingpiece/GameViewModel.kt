package com.example.missingpiece

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _hasOngoingGame = mutableStateOf(false)
    val hasOngoingGame: State<Boolean> = _hasOngoingGame

    private var savedGameState: List<List<Int>>? = null

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
