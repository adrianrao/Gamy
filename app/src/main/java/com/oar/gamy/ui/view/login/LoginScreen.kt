package com.oar.gamy.ui.view.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.oar.gamy.R
import com.oar.gamy.ui.component.CustomAlertDialog
import com.oar.gamy.ui.theme.SpaceLarge
import com.oar.gamy.ui.theme.SpaceMedium
import com.oar.gamy.ui.util.Screen
import com.oar.gamy.ui.view.component.CustomTextField
import com.oar.gamy.util.Constants

@Composable
fun LoginScreentest(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceLarge,
                end = SpaceLarge,
                top = SpaceLarge,
                bottom = 50.dp
            )
    ) {
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
                text = state.emailText,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredEmail(it))
                },
                keyboardType = KeyboardType.Email,
                error = when (state.emailError) {
                    LoginState.EmailError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    LoginState.EmailError.InvalidEmail -> {
                        stringResource(id = R.string.this_not_valid_email)
                    }
                    null -> ""
                },
                hint = stringResource(id = R.string.login_hint)
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            CustomTextField(
                text = state.passwordText,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password_hint),
                keyboardType = KeyboardType.Password,
                error = when (state.passwordError) {
                    LoginState.PasswordError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    LoginState.PasswordError.InputTooShort -> {
                        stringResource(id = R.string.input_too_short, Constants.MIN_PASSWORD_LENGTH)
                    }
                    LoginState.PasswordError.InvalidPassword -> {
                        stringResource(id = R.string.invalid_password)
                    }
                    null -> ""
                },
                isPasswordVisible = state.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    viewModel.onEvent(LoginEvent.Login)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Text(text = buildAnnotatedString {
            append(stringResource(id = R.string.dont_have_an_account_yet))
            append(" ")
            val signUpText =
                stringResource(id = R.string.sign_up)
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
            ) {
                append(signUpText)
            }
        }, style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.navigate(
                        Screen.RegisterScreen.route
                    )
                })

        val message = when (state.authenticateError) {
            LoginState.AuthenticateError.InvalidCredentials -> {
                stringResource(id = R.string.auth_invalid_credentials)
            }
            LoginState.AuthenticateError.VerifyEmail -> {
                stringResource(id = R.string.auth_verify_email)
            }
            LoginState.AuthenticateError.UserNotExist -> {
                stringResource(id = R.string.auth_user_not_exist)
            }
            null -> ""
        }
        if (message.isNotEmpty()) {
            Toast.makeText(
                LocalContext.current, message, Toast.LENGTH_SHORT
            ).show()
        }
        if (state.isAuthenticate) {
            navController.popBackStack()
            navController.navigate(
                Screen.HomeScreen.route
            )
        }
    }
}

@Composable
fun LoginScreen(
    state: LoginState,
    onNavigateToRegister: () -> Unit,
    onEvent: (LoginEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceLarge,
                end = SpaceLarge,
                top = SpaceLarge,
                bottom = 50.dp
            )
    ) {
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
                text = state.emailText,
                onValueChange = {
                    onEvent(LoginEvent.EnteredEmail(it))
                },
                keyboardType = KeyboardType.Email,
                error = when (state.emailError) {
                    LoginState.EmailError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    LoginState.EmailError.InvalidEmail -> {
                        stringResource(id = R.string.this_not_valid_email)
                    }
                    null -> ""
                },
                hint = stringResource(id = R.string.login_hint)
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            CustomTextField(
                text = state.passwordText,
                onValueChange = {
                    onEvent(LoginEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password_hint),
                keyboardType = KeyboardType.Password,
                error = when (state.passwordError) {
                    LoginState.PasswordError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cant_be_empty)
                    }
                    LoginState.PasswordError.InputTooShort -> {
                        stringResource(id = R.string.input_too_short, Constants.MIN_PASSWORD_LENGTH)
                    }
                    LoginState.PasswordError.InvalidPassword -> {
                        stringResource(id = R.string.invalid_password)
                    }
                    null -> ""
                },
                isPasswordVisible = state.isPasswordVisible,
                onPasswordToggleClick = {
                    onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    onEvent(LoginEvent.Login)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Text(text = buildAnnotatedString {
            append(stringResource(id = R.string.dont_have_an_account_yet))
            append(" ")
            val signUpText =
                stringResource(id = R.string.sign_up)
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
            ) {
                append(signUpText)
            }
        }, style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    onNavigateToRegister()
                })

        val message = when (state.authenticateError) {
            LoginState.AuthenticateError.InvalidCredentials -> {
                stringResource(id = R.string.auth_invalid_credentials)
            }
            LoginState.AuthenticateError.VerifyEmail -> {
                stringResource(id = R.string.auth_verify_email)
            }
            LoginState.AuthenticateError.UserNotExist -> {
                stringResource(id = R.string.auth_user_not_exist)
            }
            null -> ""
        }
        if (message.isNotEmpty()) {
            CustomAlertDialog(mutableStateOf(true))
        }

    }
}