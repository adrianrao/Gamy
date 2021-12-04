package com.oar.gamy.ui.util

sealed class Screen(
    val route: String
){
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("main_screen")
    object ChatScreen : Screen("chat_screen")
    object PlayersScreen : Screen("players_screen")
    object RentalScreen : Screen("rental_screen")
    object AccountScreen : Screen("account_screen")
}
