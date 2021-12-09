package com.oar.gamy.ui.util

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oar.gamy.R
import com.oar.gamy.domain.repository.UserRepository
import com.oar.gamy.ui.view.account.AccountScreen
import com.oar.gamy.ui.view.chat.ChatScreen
import com.oar.gamy.ui.view.home.HomeScreen
import com.oar.gamy.ui.view.login.LoginScreen
import com.oar.gamy.ui.view.login.LoginState
import com.oar.gamy.ui.view.login.LoginViewModel
import com.oar.gamy.ui.view.players.PlayersScreen
import com.oar.gamy.ui.view.register.RegisterScreen
import com.oar.gamy.ui.view.register.RegisterState
import com.oar.gamy.ui.view.register.RegisterViewModel
import com.oar.gamy.ui.view.rental.RentalScreen
import com.oar.gamy.ui.view.splash.SplashScreen
import com.oar.gamy.ui.view.splash.SplashViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        addSplash(navController)
        addLogin(navController)
        addHome(navController)
        addChat(navController)
        addPlayers(navController)
        addRental(navController)
        addAccount(navController)
        addRegister(navController)
    }
}


private fun NavGraphBuilder.addSplash(navController: NavHostController) {
    composable(Screen.SplashScreen.route) {
        val viewModel: SplashViewModel = viewModel()

        SplashScreen(navController = navController) {
            viewModel.onEvent(it)
        }

        if (viewModel.state.value.isAuthenticate != null) {
            if (viewModel.state.value.isAuthenticate!!) {
                LaunchedEffect(key1 = Unit) {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            } else {
                LaunchedEffect(key1 = Unit) {
                    navController.popBackStack()
                    navController.navigate(Screen.LoginScreen.route)
                }
            }
        }

    }
}

private fun NavGraphBuilder.addLogin(navController: NavHostController) {
    composable(Screen.LoginScreen.route) {
        val viewModel: LoginViewModel = viewModel()

        if (viewModel.state.value.isAuthenticate) {
            LaunchedEffect(key1 = Unit) {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            }
        } else {
            LoginScreen(
                state = viewModel.state.value,
                onNavigateToRegister = {
                    navController.popBackStack()
                    navController.navigate(Screen.RegisterScreen.route)
                },
                onEvent = {
                    viewModel.onEvent(it)
                }
            )
        }
    }
}

private fun NavGraphBuilder.addHome(navController: NavHostController) {
    composable(Screen.HomeScreen.route) {
        HomeScreen(navController = navController)
    }
}

private fun NavGraphBuilder.addChat(navController: NavHostController) {
    composable(Screen.ChatScreen.route) {
        ChatScreen(navController = navController)
    }
}

private fun NavGraphBuilder.addPlayers(navController: NavHostController) {
    composable(Screen.PlayersScreen.route) {
        PlayersScreen(navController = navController)
    }
}

private fun NavGraphBuilder.addRental(navController: NavHostController) {
    composable(Screen.RentalScreen.route) {
        RentalScreen(navController = navController)
    }
}

private fun NavGraphBuilder.addAccount(navController: NavHostController) {
    composable(Screen.AccountScreen.route) {
        AccountScreen(navController = navController) {
            val repository = UserRepository()
            navController.popBackStack()
            repository.signOut()
            navController.navigate(Screen.LoginScreen.route)
        }
    }
}

private fun NavGraphBuilder.addRegister(navController: NavHostController) {
    composable(Screen.RegisterScreen.route) {
        val viewModel: RegisterViewModel = viewModel()

        val message = when (viewModel.state.value.registrationMessage) {
            RegisterState.RegisterMessage.AlreadyExistUser -> {
                stringResource(id = R.string.user_already_exist)
            }
            RegisterState.RegisterMessage.UserCreated -> {
                stringResource(id = R.string.user_created)
            }
            null -> ""
        }

        if (message.isNotEmpty()) {
            Toast.makeText(
                LocalContext.current, message, Toast.LENGTH_SHORT
            ).show()
        }

        RegisterScreen(
            viewModel.state.value,
            onNavigateToLogin = {
                navController.popBackStack()
                navController.navigate(Screen.LoginScreen.route)
            },
            onEvent = {
                viewModel.onEvent(it)
            }
        )
    }
}