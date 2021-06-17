package com.example.pwameme.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.AlertDialogItem
import com.example.pwameme.ui.screens.component.ButtonClickItem
import com.example.pwameme.ui.screens.component.TextFieldItem
import com.example.pwameme.ui.screens.component.TextFieldState
import com.example.pwameme.util.Constants
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.NO_USERNAME

@Composable
fun AddScreen(navController:NavHostController) {
    val AuthVM = hiltViewModel<AuthViewModel>()
    if(AuthVM.isLoggedIn()){
        AuthVM.usernamevm?.let { AuthVM.passwordvm?.let { it1 -> AuthVM.authenticateApi(it, it1) } }
    }
    val username = AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME
    if (username == NO_USERNAME){
        AlertDialogItem(title = "Please Login", text = "You need to login first to add Meme. Do you want to Login?",
        onClick = { navController.navigate("LoginRoute") })
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "AddScreen Screen $username")
        val lists = listOf<String>(
            "lmao", "lol", "laugh", "dang", "lal"
        )
        val numberState = remember { TextFieldState() }
        var visible by remember { mutableStateOf(false) }
        Text(text = "Input how many words you want to take:")
        TextFieldItem(
            modifier = Modifier.fillMaxWidth(0.2f),
            numberState,
            KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        if (visible) {
            val x = numberState.text.toInt()
            Column {
                var listkeyword = mutableListOf<String>()
                for (i in 1..x) {
                    val y = lists.random()
                    listkeyword.add(y)
                }
                Text(text = "$listkeyword")
                ButtonClickItem(desc = "Trash", onClick = {})
                ButtonClickItem(desc = "Make it", onClick = {})
            }

        } else {
            ButtonClickItem(desc = "Random", onClick = {
                if (1 == 0) {
                    visible = !visible
                }
            })
        }
    }
}