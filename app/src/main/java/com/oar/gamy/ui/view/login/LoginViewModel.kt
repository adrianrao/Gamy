package com.oar.gamy.ui.view.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {


    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _showPassword = mutableStateOf(false)
    val showPassword: State<Boolean> = _showPassword

    private val _usernameError = mutableStateOf("")
    val usernameError: State<String> = _usernameError

    private val _passwordError = mutableStateOf("")
    val passwordError: State<String> = _passwordError

    private val _isAuthenticate = mutableStateOf(false)
    val isAuthenticate: State<Boolean> = _isAuthenticate

    fun setUsernameText(username: String) {
        _usernameText.value = username
    }

    fun setPasswordText(password: String) {
        _passwordText.value = password
    }

    fun setIsUsernameError(error: String) {
        _usernameError.value = error
    }

    fun setIsPasswordError(error: String) {
        _passwordError.value = error
    }

    fun setShowPassword(showPassword: Boolean) {
        _showPassword.value = showPassword
    }

    fun authenticateUser(usernameText:String,passwordText: String){
        if(usernameText != "" && usernameText != ""){
            _isAuthenticate.value = true
        }
    }

}