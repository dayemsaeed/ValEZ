package com.lumen.valez_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme
import com.lumen.valez_android.ui.view.CarDetailsScreen
import com.lumen.valez_android.ui.view.CarList
import com.lumen.valez_android.ui.view.CheckInScreen
import com.lumen.valez_android.ui.view.EditScreen
import com.lumen.valez_android.ui.view.LoginScreen
import com.lumen.valez_android.ui.view.RegisterScreen
import com.lumen.valez_android.ui.view.TicketScanScreen
import com.lumen.valez_android.ui.view.VinScanScreen
import com.lumen.valez_android.viewmodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val carViewModel: CarViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "listScreen") {
        composable("listScreen") { CarList(navController, carViewModel) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("checkIn") { CheckInScreen(navController, carViewModel) }
        composable("vinScan") { VinScanScreen(navController, carViewModel) }
        composable("ticketScan") { TicketScanScreen(navController, carViewModel) }
        // Define the details screen composable
        composable(
            route = "car_detail/{ticketId}",
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Retrieve the ticketId from the backStackEntry
            val ticketId = backStackEntry.arguments?.getString("ticketId")
            // Use the ticketId to display the details
            CarDetailsScreen(ticketId = ticketId, navController = navController, carViewModel)
        }
        composable(
            route = "edit/{ticketId}",
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId")
            EditScreen(ticketId, navController, carViewModel)
        }
        // You can uncomment and keep the error composable if needed
        /*composable("error") { backStackEntry ->
            val errorMessage = backStackEntry.arguments?.getString("errorMessage")
            ErrorScreen(errorMessage = errorMessage ?: "Unknown Error")
        }*/
    }
}

@AndroidEntryPoint
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
                    AppNavigation()
                }
            }
        }
    }
}