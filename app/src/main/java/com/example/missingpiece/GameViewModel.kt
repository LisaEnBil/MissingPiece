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


class GameViewModel() : ViewModel() {


    private val _difficulty = mutableIntStateOf(2)
    val difficulty: State<Int> = _difficulty

    private val _score = mutableIntStateOf(0)
    val score: State<Int> = _score

    private val _hasOngoingGame = mutableStateOf(false)

    val hasOngoingGame: State<Boolean> = _hasOngoingGame

    private var savedGameState: List<List<Int>>? = null

    @Composable
    fun saveHighScore(name: String, score: Int) {
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putInt(name, score)
            apply()
        }
    }
    @Composable
    fun getHighScores(): List<Pair<String, Int>> {
        val context = LocalContext.current

        val sharedPreferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE)

        return sharedPreferences.all.map {
            Pair(it.key, it.value as Int)
        }.sortedByDescending { it.second }
    }

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
