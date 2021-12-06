package com.oar.gamy.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@Composable
fun CustomAlertDialog(
    showDialog : MutableState<Boolean> = mutableStateOf(false)
) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text(text = "Dialog") },
        confirmButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                showDialog.value = false
            }) {
                Text(text = "Dissmis")
            }
        }
    ) 
}