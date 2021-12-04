package com.oar.gamy.ui.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oar.gamy.ui.view.account.AccountScreen
import com.oar.gamy.ui.view.chat.ChatScreen
import com.oar.gamy.ui.view.home.HomeScreen
import com.oar.gamy.ui.view.login.LoginScreen
import com.oar.gamy.ui.view.players.PlayersScreen
import com.oar.gamy.ui.view.register.RegisterScreen
import com.oar.gamy.ui.view.rental.RentalScreen
import com.oar.gamy.ui.view.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
        composable(Screen.PlayersScreen.route){
            PlayersScreen(navController = navController)
        }
        composable(Screen.RentalScreen.route){
            RentalScreen(navController = navController)
        }
        composable(Screen.AccountScreen.route){
            AccountScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
    }
}