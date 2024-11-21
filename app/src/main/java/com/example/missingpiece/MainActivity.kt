package com.example.missingpiece

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    private val hsViewModel: HighScoreViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HighScoreViewModel(applicationContext) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppNavHost(viewModel, hsViewModel)
        }
    }
}

@Composable
fun MyAppNavHost(
    viewModel: GameViewModel,
    hsViewModel: HighScoreViewModel,
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
                hsViewModel = hsViewModel,
                goToGame = { navController.navigate(NavScreen.Game.name) },
                onBackPressed = { navController.popBackStack() },
                goToStart = { navController.navigate(NavScreen.Start.name) });
        }
        composable(NavScreen.HighScore.name) {
            HighScore(hsViewModel = hsViewModel);
        }
        composable(NavScreen.Settings.name) {
            Settings( viewModel = viewModel);
        }
    }
}
