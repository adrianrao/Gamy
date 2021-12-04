package com.oar.gamy.ui.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.oar.gamy.domain.repository.UserRepository
import com.oar.gamy.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModelState : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _state.value = LoginState(emailText = event.value)
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = LoginState(passwordText = event.value)
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _state.value = LoginState(isPasswordVisible = !state.value.isPasswordVisible)
            }
            is LoginEvent.Login -> {
                validateEmail(_state.value.emailText)
                validatePassword(_state.value.passwordText)
                authenticateUser()
            }

        }
    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _state.value = LoginState(emailError = LoginState.EmailError.FieldEmpty)
            return
        }
        _state.value = LoginState(emailError = null)
    }

    private fun validatePassword(password: String) {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isBlank()) {
            _state.value = LoginState(passwordError = LoginState.PasswordError.FieldEmpty)
        }

        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = LoginState(passwordError = LoginState.PasswordError.InputTooShort)
            return
        }

        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLettersInPassword || !numberInPassword) {
            _state.value = LoginState(passwordError = LoginState.PasswordError.InvalidPassword)
            return
        }
        _state.value = LoginState(passwordError = null)
    }

    private fun authenticateUser() {
        var email = if (_state.value.emailError == null) _state.value.emailText else return
        var password = if (_state.value.passwordError == null) _state.value.passwordText else return
        viewModelScope.launch {
            val repository = UserRepository()
            repository.authenticateUser(email, password) { isLoged, error ->
                if (isLoged) {
                    _state.value = LoginState(isAuthenticate = true)
                } else {
                    when (error) {
                        is FirebaseAuthInvalidUserException -> {
                            _state.value = LoginState(authenticateError = LoginState.AuthenticateError.UserNotExist)
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            _state.value = LoginState(authenticateError = LoginState.AuthenticateError.InvalidCredentials)
                        }
                        else -> {
                            _state.value = LoginState(authenticateError = LoginState.AuthenticateError.VerifyEmail)
                        }
                    }
                }
            }
        }
    }

}