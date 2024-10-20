package com.example.missingpiece

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.missingpiece.ui.theme.MissingPieceTheme

@Composable
fun Start(){
    Column {
        Text("Start")
        
        Button(onClick = { /*TODO*/ }) {
            Text("START GAME")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun StartPreview() {
    MissingPieceTheme {
        Start()
    }
}