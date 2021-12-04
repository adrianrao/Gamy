package com.oar.gamy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oar.gamy.ui.theme.GamyTheme
import com.oar.gamy.ui.util.Navigation
import com.oar.gamy.ui.util.Screen
import com.oar.gamy.ui.view.component.CustomScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GamyTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    CustomScaffold(
                        navController = navController,
                        showBottomBar = navBackStackEntry?.destination?.route in listOf(
                            Screen.HomeScreen.route,
                            Screen.PlayersScreen.route,
                            Screen.RentalScreen.route,
                            Screen.AccountScreen.route,
                            Screen.ChatScreen.route,
                        ),
                        onFabClick = { navController.navigate(Screen.ChatScreen.route) }
                    ) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }
}