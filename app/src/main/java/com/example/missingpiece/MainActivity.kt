package com.example.missingpiece

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class NavScreen() {
    Start,
    HighScore,
    Game,
    Settings
}


class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppNavHost(viewModel)
        }
    }
}

@Composable
fun MyAppNavHost(
    viewModel: GameViewModel,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = NavScreen.Start.name) {
        composable(NavScreen.Start.name) {
            Start(
                goToGame = { navController.navigate(NavScreen.Game.name) },
                goToHighScore = { navController.navigate(NavScreen.HighScore.name) },
                goToSettings = { navController.navigate(NavScreen.Settings.name) },
                viewModel = viewModel,
            );
        }
        composable(NavScreen.Game.name) {
            Game(
                viewModel = viewModel,
                goToGame = { navController.navigate(NavScreen.Game.name) },
                onBackPressed = { navController.popBackStack() },
                goToStart = { navController.navigate(NavScreen.Start.name) });
        }
        composable(NavScreen.HighScore.name) {
            HighScore(viewModel = viewModel);
        }
        composable(NavScreen.Settings.name) {
            Settings( viewModel = viewModel);
        }
    }
}
