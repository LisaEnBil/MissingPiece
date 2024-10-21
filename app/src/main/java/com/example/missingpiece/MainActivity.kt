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
    Start,
    HighScore,
    GameInstructions,
    Game
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
    NavHost(navController = navController, startDestination = NavScreen.Start.name ){
        composable(NavScreen.Start.name){
            Start(
                goToGame = { navController.navigate(NavScreen.Game.name)},
                goToHighScore = { navController.navigate(NavScreen.HighScore.name)},
                goToInstructions = { navController.navigate(NavScreen.GameInstructions.name)}
            );
        }
        composable(NavScreen.Game.name){
            Game();
        }
        composable(NavScreen.HighScore.name){
            HighScore();
        }
        composable(NavScreen.GameInstructions.name){
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