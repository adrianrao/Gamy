package com.oar.gamy.ui.view.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _showButtomBar = mutableStateOf(false)
    val showButtonBar: State<Boolean> = _showButtomBar

    fun setButtonBar(showButtonBar: Boolean) {
        _showButtomBar.value = showButtonBar
    }
}