package com.example.missingpiece

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.missingpiece.ui.theme.Blue10
import com.example.missingpiece.ui.theme.MissingPieceTheme



@Composable
fun HighScore(viewModel: GameViewModel,context: Context) {

    var highScores by remember { mutableStateOf(emptyList<ScoreEntry>()) }

    LaunchedEffect(Unit) {
        HighScoreManager.getTopScores(context).collect { scores ->
            highScores = scores
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("High Scores", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        highScores.forEachIndexed { index, scoreEntry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text("${index + 1}. ${scoreEntry.name}", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.weight(1f))
                Text(scoreEntry.score.toString(), fontWeight = FontWeight.Bold)
            }
        }
    }
}

