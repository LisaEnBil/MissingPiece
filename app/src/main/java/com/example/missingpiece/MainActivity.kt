package com.example.missingpiece

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.missingpiece.ui.theme.MissingPieceTheme

enum class NavScreen(){
    start,
    highScore,
    gameInstructions,
    game
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppNavHost()
        }
    }
}

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController()
    ) {
    NavHost(navController = navController, startDestination = NavScreen.start.name ){
        composable(NavScreen.start.name){
            Start(
                goToGame = { navController.navigate(NavScreen.game.name)},
                goToHighScore = { navController.navigate(NavScreen.highScore.name)},
                goToInstructions = { navController.navigate(NavScreen.gameInstructions.name)}
            );
        }
        composable(NavScreen.game.name){
            Game();
        }
        composable(NavScreen.highScore.name){
            HighScore();
        }
        composable(NavScreen.gameInstructions.name){
            GameInstructions();
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavHostPreview() {
    MissingPieceTheme {
        MyAppNavHost()
    }
}