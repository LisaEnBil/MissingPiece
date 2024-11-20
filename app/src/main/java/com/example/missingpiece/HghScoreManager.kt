package com.example.missingpiece

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object DataStoreManager {
    private val Context.dataStore by preferencesDataStore(name = "high_scores")

    fun getDataStore(context: Context) = context.dataStore
}

@kotlinx.serialization.Serializable
data class ScoreEntry(val name: String, val score: Int)

object HighScoreManager {
    private const val MAX_SCORES = 10
    private val SCORES_KEY = stringPreferencesKey("high_scores")



    suspend fun saveScore(context: Context, name: String, score: Int) {
        DataStoreManager.getDataStore(context).edit { prefs ->
            val currentScores = Json.decodeFromString<List<ScoreEntry>>(
                prefs[SCORES_KEY] ?: "[]"
            ).toMutableList()

            currentScores.add(ScoreEntry(name, score))
            currentScores.sortByDescending { it.score }

            val topScores = currentScores.take(MAX_SCORES)
            prefs[SCORES_KEY] = Json.encodeToString<List<ScoreEntry>>(topScores)

            Log.i("SCORE", topScores.toString())
        }
    }

    fun getTopScores(context: Context): Flow<List<ScoreEntry>> {
        return DataStoreManager.getDataStore(context).data
            .catch { e ->
                Log.e("HighScoreManager", "Error reading scores", e)
                emit(emptyPreferences())
            }
            .map { prefs ->
                try {
                    Json.decodeFromString<List<ScoreEntry>>(prefs[SCORES_KEY] ?: "[]")
                } catch (e: Exception) {
                    Log.e("HighScoreManager", "Error decoding scores", e)
                    emptyList()
                }
            }
    }
/*
    suspend fun isHighScore(context: Context, score: Int): Boolean {
        val scores = getTopScores(context).map { scores ->
            scores.size < MAX_SCORES || score > (scores.lastOrNull()?.score ?: 0)
        }
        return scores.first()
    }

    suspend fun clearHighScores(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(SCORES_KEY)
        }
    }*/
}