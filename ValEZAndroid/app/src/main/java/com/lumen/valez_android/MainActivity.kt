package com.lumen.valez_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme
import com.lumen.valez_android.ui.view.LoginScreen
import com.lumen.valez_android.ui.view.RegisterScreen
import com.lumen.valez_android.ui.view.VinScanScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("vinScan") { VinScanScreen() }
        /*composable("error") { backStackEntry ->
            val errorMessage = backStackEntry.arguments?.getString("errorMessage")
            ErrorScreen(errorMessage = errorMessage ?: "Unknown Error")
        }*/
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValEZAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VinScanScreen()
                }
            }
        }
    }
}