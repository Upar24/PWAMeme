package com.example.pwameme.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.*
import com.example.pwameme.util.Constants
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_PASSWORD
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Status
import timber.log.Timber

@Composable
fun RegisterScreen(navController: NavHostController, vm: AuthViewModel= viewModel()){

    val uiState= vm.registerStatus.observeAsState()
    Register(navController,vm)
    uiState.value?.let {
        val result = it.peekContent()
        when(result.status){
            Status.SUCCESS -> {
                Toast.makeText(LocalContext.current,result.data ?: "successfully registered",
                    Toast.LENGTH_SHORT).show()
                navController.navigate("LoginRoute")
            }
            Status.ERROR -> {
                Toast.makeText(LocalContext.current, result.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressCardToastItem()
            }
        }
    }
    Text("Register Screen Hmm LOL")
}
@Composable
fun Register(navController: NavHostController, vm: AuthViewModel) {
    val usernameState= remember{ TextFieldState() }
    val passwordState= remember{ TextFieldState() }
    val repeatPasswordState= remember{ TextFieldState() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.size(70.dp))
        TextFieldOutlined("Username",usernameState)
        Spacer(Modifier.size(7.dp))
        TextFieldOutlined("Password",passwordState)
        Spacer(Modifier.size(7.dp))
        TextFieldOutlined("Repeat Password",repeatPasswordState)
        Spacer(Modifier.size(40.dp))
        ButtonClickItem("Register",onClick= {
            vm.registerUser(usernameState.text,passwordState.text,repeatPasswordState.text)
        })
        Spacer(modifier = Modifier.padding(24.dp))
        SwitchTOLoginOrRegisterTexts(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text1 = "Don't have an account yet?",
            text2 = "Login"
        ) {
            navController.navigate("LoginRoute"){
                navController.graph.startDestinationRoute?.let {
                    popUpTo(it){
                        saveState=true
                    }
                }
                launchSingleTop=true
                restoreState=true
            }
        }
    }
}

@Composable
fun LoginScreen(
    navController: NavHostController,
    vm: AuthViewModel= viewModel()
){
    val uiState= vm.loginStatus.observeAsState()
    Login(navController,vm)
    uiState.value?.let {
        val result= it.peekContent()
        when(result.status){
            Status.SUCCESS -> {
                Toast.makeText(LocalContext.current, result.data ?: "successfully logged in",
                    Toast.LENGTH_SHORT).show()
                vm.sharedPref.edit().putString(KEY_LOGGED_IN_USERNAME,vm.usernamevm).apply()
                vm.sharedPref.edit().putString(KEY_LOGGED_IN_PASSWORD,vm.passwordvm).apply()
                vm.getDesc((if(vm.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) == NO_USERNAME) Constants.LOGIN else Constants.LOGOUT))
                vm.authenticateApi(vm.usernamevm ?: "", vm.passwordvm ?: "")
                navController.navigate("Home")
                Timber.d("Called")
            }
            Status.ERROR -> {
                Toast.makeText(LocalContext.current, result.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressBarItem()
            }
        }
    }
    Text("Login Screen Hmm LOL ${vm.passwordvm} ${vm.usernamevm} " +
            (vm.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME)
    )
}
@Composable
fun Login(navController: NavHostController, vm: AuthViewModel) {
    val usernameState= remember{ TextFieldState() }
    val passwordState= remember{ TextFieldState() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.size(70.dp))
        TextFieldOutlined("Username",usernameState)
        Spacer(Modifier.size(7.dp))
        TextFieldOutlined("Password",passwordState)
        Spacer(Modifier.size(40.dp))
        ButtonClickItem("Login",onClick= {
            vm.loginUser(usernameState.text,passwordState.text)
        })
        Spacer(modifier = Modifier.padding(24.dp))
        SwitchTOLoginOrRegisterTexts(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text1 = "Don't have an account yet?",
            text2 = "Register"
        ) {
            navController.navigate("RegisterRoute"){
                navController.graph.startDestinationRoute?.let {
                    popUpTo(it){
                        saveState=true
                    }
                }
                launchSingleTop=true
                restoreState=true
            }
        }
    }


}
