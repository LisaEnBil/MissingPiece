package com.example.missingpiece

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HighScoreViewModel(context: Context) : ViewModel() {

    private val _highScoreList = MutableStateFlow<List<User>?>(null)
    val highScoreList: StateFlow<List<User>?> get() = _highScoreList

    private val database by lazy { AppDatabase.getDatabase(context) }
    private val userDao by lazy { database.userDao() }

    fun addHighScore(name: String, score: Int, size: Int) {
        if(name == "") {
            return
        }

        val tempUser = User(0, name, score, size)

        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertAll(tempUser)
        }
    }

    fun getHighScore() {
        CoroutineScope(Dispatchers.IO).launch {
            val allPosts = userDao.getTopTenUsers()

            _highScoreList.value = allPosts
        }
    }

}
