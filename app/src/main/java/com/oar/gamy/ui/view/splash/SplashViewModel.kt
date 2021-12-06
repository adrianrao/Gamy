package com.oar.gamy.ui.view.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.oar.gamy.domain.repository.UserRepository

class SplashViewModel : ViewModel() {

    private val _state = mutableStateOf(SplashState())
    val state : State<SplashState> = _state

    fun onEvent(event : SplashEvent){
        when(event){
            is SplashEvent.Authenticate -> {
                authenticateUser()
            }
        }
    }

    private fun authenticateUser(){
        val repository = UserRepository()
        if(repository.userIsLoged()){
            _state.value = _state.value.copy(
                isAuthenticate = true
            )
        }else{
            _state.value = _state.value.copy(
                isAuthenticate = false
            )
        }
    }
}