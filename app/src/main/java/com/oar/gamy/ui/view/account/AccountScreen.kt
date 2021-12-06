package com.oar.gamy.ui.view.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.oar.gamy.R
import com.oar.gamy.domain.repository.UserRepository

@Composable
fun AccountScreen(
    navController: NavHostController,
    goToLogin: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Account")
        Button(onClick = { goToLogin() }) {
            Text(text = stringResource(R.string.sign_out))
        }
    }
}