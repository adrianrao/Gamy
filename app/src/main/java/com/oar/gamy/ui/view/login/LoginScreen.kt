package com.oar.gamy.ui.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.oar.gamy.R
import com.oar.gamy.ui.theme.SpaceLarge
import com.oar.gamy.ui.theme.SpaceMedium
import com.oar.gamy.ui.view.component.CustomTextField
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oar.gamy.ui.util.Screen

@Composable
fun LoginScreen(
    navController : NavHostController,
    viewModel : LoginViewModel = viewModel()
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(
            start = SpaceLarge,
            end = SpaceLarge,
            top = SpaceLarge,
            bottom = 50.dp
        )){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h1,
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            CustomTextField(
                text = viewModel.usernameText.value,
                onValueChange = {
                    viewModel.setUsernameText(it)
                },
                keyboardType = KeyboardType.Email,
                error = viewModel.usernameError.value,
                hint = stringResource(id = R.string.login_hint)
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            CustomTextField(
                text = viewModel.passwordText.value,
                onValueChange = {
                    viewModel.setPasswordText(it)
                },
                hint = stringResource(id = R.string.password_hint),
                keyboardType = KeyboardType.Password,
                error = viewModel.passwordError.value,
                isPasswordVisible = viewModel.showPassword.value,
                onPasswordToggleClick = {
                    viewModel.setShowPassword(it)
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(
                onClick = {
                    viewModel.authenticateUser(viewModel.usernameText.value,viewModel.passwordText.value)
                    if(viewModel.isAuthenticate.value){
                        navController.popBackStack()
                        navController.navigate(
                            Screen.HomeScreen.route
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}