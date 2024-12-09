package com.example.missingpiece

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class GameViewModel() : ViewModel() {

    private val _difficulty = mutableIntStateOf(3)
    val difficulty: State<Int> = _difficulty

    private val _score = mutableIntStateOf(0)
    val score: State<Int> = _score

    private val _isShowingDifficultyLevels = mutableStateOf(false)
    val isShowingDifficultyLevels: State<Boolean> = _isShowingDifficultyLevels

    private val _hasOngoingGame = mutableStateOf(false)
    val hasOngoingGame: State<Boolean> = _hasOngoingGame

    private val _hasFinishedGame = mutableStateOf(false)
    val hasFinishedGame: State<Boolean> = _hasFinishedGame

    private var savedGameState: List<List<Int>>? = null

    private val _isResetComplete = mutableStateOf(false)
    val isResetComplete: State<Boolean> = _isResetComplete

    fun completeReset() {
        _isResetComplete.value = false
        return
    }

    fun setHighScore() {
        _score.intValue += 1
    }

    fun resetHighScore() {
        _score.intValue = 0
    }

    fun resetGame() {
        _hasOngoingGame.value = false
        _score.intValue = 0
        _hasFinishedGame.value = false
        _hasOngoingGame.value = false
    }

    fun setIsShowingDifficultyLevels() {
        _isShowingDifficultyLevels.value = !_isShowingDifficultyLevels.value
        return
    }

    fun setDifficulty(gridSize: Int) {
        _difficulty.intValue = gridSize
    }

    fun saveGameState(gameState: List<List<Int>>) {
        savedGameState = gameState
        _hasOngoingGame.value = true
    }

    fun getSavedGameState(): List<List<Int>>? = savedGameState

    fun hasFinishedPuzzle() {
        _hasFinishedGame.value = true
        return
    }

    fun clearSavedGame() {
        _isResetComplete.value = true
        _hasOngoingGame.value = false
        savedGameState = null
    }
}
