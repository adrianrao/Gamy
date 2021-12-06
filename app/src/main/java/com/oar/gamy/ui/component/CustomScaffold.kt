package com.oar.gamy.ui.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oar.gamy.R
import com.oar.gamy.domain.model.BottomNavItem
import com.oar.gamy.ui.util.Screen

@Composable
fun CustomScaffold(
    navController: NavController,
    showBottomBar: Boolean = false,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.HomeScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = "Home"
        ),
        BottomNavItem(
            route = Screen.PlayersScreen.route,
            icon = Icons.Outlined.PersonAdd,
            contentDescription = "Players"
        ),
        BottomNavItem(
            route = Screen.RentalScreen.route,
            icon = Icons.Outlined.CalendarToday,
            contentDescription = "Rental"
        ),
        BottomNavItem(
            route = Screen.AccountScreen.route,
            icon = Icons.Outlined.AccountCircle,
            contentDescription = "Account"
        )
    ),
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(bottomBar = {
        if(showBottomBar){
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.surface,
                cutoutShape = CircleShape,
                elevation = 5.dp
            ){
                BottomNavigation{
                    bottomNavItems.forEach{ bottomNavItem ->
                        CustomBottomNavItem(
                            icon = bottomNavItem.icon,
                            contentDescription = bottomNavItem.contentDescription,
                            selected = bottomNavItem.route == navController.currentDestination?.route,
                            alertCount = bottomNavItem.alertCount,
                            enabled = bottomNavItem.icon != null
                        ) {
                            if (navController.currentDestination?.route != bottomNavItem.route) {
                                navController.popBackStack()
                                navController.navigate(bottomNavItem.route)
                            }
                        }
                    }
                }
            }
        }
    },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = onFabClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = stringResource(id = R.string.chat)
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        content()
    }
}