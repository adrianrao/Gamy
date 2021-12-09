package com.oar.gamy.ui.view.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.oar.gamy.domain.repository.UserRepository
import com.oar.gamy.util.Constants
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state : State<LoginState> = _state


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _state.value = _state.value.copy(
                    emailText = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    passwordText = event.value
                )
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _state.value = state.value.copy(
                    isPasswordVisible = !state.value.isPasswordVisible
                )
            }
            is LoginEvent.Login -> {
                validateEmail(_state.value.emailText)
                validatePassword(_state.value.passwordText)
                authenticateUser()
            }
            is LoginEvent.ResetMessageError -> {
                _state.value = state.value.copy(
                    authenticateError = null
                )
            }
        }
    }

    private fun validatePassword(password: String) {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isBlank()) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.FieldEmpty
            )
        }

        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.InputTooShort
            )
            return
        }

        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.InvalidPassword
            )
            return
        }
        _state.value = _state.value.copy(passwordError = null)
    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _state.value = _state.value.copy(
                emailError = LoginState.EmailError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(
            emailError = null
        )
    }

    private fun authenticateUser() {
        var email = if (_state.value.emailError == null) _state.value.emailText else return
        var password = if (_state.value.passwordError == null) _state.value.passwordText else return
        viewModelScope.launch {
            val repository = UserRepository()
            repository.authenticateUser(email, password) { isLoged, error ->
                if (isLoged) {
                    _state.value = _state.value.copy(
                        isAuthenticate = true
                    )
                } else {
                    when (error) {
                        is FirebaseAuthInvalidUserException -> {
                            _state.value = _state.value.copy(
                                authenticateError = LoginState.AuthenticateError.UserNotExist
                            )
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            _state.value = _state.value.copy(
                                authenticateError = LoginState.AuthenticateError.InvalidCredentials
                            )
                        }
                        else -> {
                            _state.value = _state.value.copy(
                                authenticateError = LoginState.AuthenticateError.VerifyEmail
                            )
                        }
                    }
                }
            }
        }
    }

}