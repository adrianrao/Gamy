package com.oar.gamy.ui.view.login

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object ResetMessageError : LoginEvent()
    object Login : LoginEvent()
}
