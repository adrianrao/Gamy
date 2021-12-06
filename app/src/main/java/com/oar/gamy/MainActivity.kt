package com.oar.gamy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oar.gamy.ui.theme.GamyTheme
import com.oar.gamy.ui.util.Navigation
import com.oar.gamy.ui.util.Screen
import com.oar.gamy.ui.component.CustomScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GamyTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.background
                ) {
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