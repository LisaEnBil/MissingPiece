package com.example.missingpiece

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.missingpiece.ui.theme.Blue10
import com.example.missingpiece.ui.theme.MissingPieceTheme

@Composable
fun Game(){
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){  Text(text = "Game", color = Blue10) }
}



@Preview(showBackground = true)
@Composable
fun GamePreview() {
    MissingPieceTheme {
        Game()
    }
}