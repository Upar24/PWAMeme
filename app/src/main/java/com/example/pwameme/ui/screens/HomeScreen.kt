package com.example.pwameme.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.ButtonClickItem
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.NO_USERNAME

@Composable
fun HomeScreen(AuthVM: AuthViewModel= viewModel()){
        Text("Home Screen Hmm LOL${AuthVM.passwordvm} ${AuthVM.usernamevm} " +
                (AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME)
        )

    if(AuthVM.isLoggedIn()){
        AuthVM.usernamevm?.let { AuthVM.passwordvm?.let { it1 -> AuthVM.authenticateApi(it, it1) } }
    }

}