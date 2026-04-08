package com.echospark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.echospark.presentation.screens.home.HomeScreen
import com.echospark.presentation.screens.game.GameScreen
import com.echospark.presentation.screens.shop.SparkShopScreen
import com.echospark.presentation.screens.quest.QuestScreen
import com.echospark.presentation.screens.progress.ProgressScreen
import com.echospark.ui.theme.EchoAndSparkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EchoAndSparkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onGameStart = { navController.navigate("game") },
                onQuestClick = { navController.navigate("quest") },
                onShopClick = { navController.navigate("shop") }
            )
        }
        composable("game") {
            GameScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("shop") {
            SparkShopScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("quest") {
            QuestScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("progress") {
            ProgressScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    EchoAndSparkTheme {
        AppNavigation()
    }
}
