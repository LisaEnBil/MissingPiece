package com.example.missingpiece

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.missingpiece.ui.theme.MissingPieceTheme

@Composable
fun Game(){
    Text(text = "Game")
}



@Preview(showBackground = true)
@Composable
fun GamePreview() {
    MissingPieceTheme {
        Game()
    }
}