package com.example.pwameme.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.*
import com.example.pwameme.ui.screens.creatememe.CreateMemeViewModel
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.MEME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Constants.TRASH
import com.example.pwameme.util.Status

@Composable
fun AddScreen(navController:NavHostController) {
    val createVM = hiltViewModel<CreateMemeViewModel>()
    val AuthVM = hiltViewModel<AuthViewModel>()
    if(AuthVM.isLoggedIn()){
        AuthVM.usernamevm?.let { AuthVM.passwordvm?.let { it1 -> AuthVM.authenticateApi(it, it1) } }
    }
    val username = AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME
    if (username == NO_USERNAME){
        AlertDialogItem(title = "Please Login", text = "You need to login first to add Meme. Do you want to Login?",
        onClick = { navController.navigate("LoginRoute") })
    }
    val numberState = remember { TextFieldState() }
    var visibleOptionsPost by remember { mutableStateOf(false) }
    var visible2 by remember { mutableStateOf(false)}
    var visible3 by remember { mutableStateOf(false)}
    var memePic by remember { mutableStateOf("") }
    val resultState = remember {TextFieldState()}
    var listkeyword by remember { mutableStateOf(mutableListOf(""))}


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "AddScreen Screen $username")
        Text(text = "Input how many words you want to take:")
        TextFieldItem(
            modifier = Modifier.fillMaxWidth(0.2f),
            numberState,
            KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        ButtonClickItem(desc = "Generate", onClick = {
            createVM.decreaseScore(username,numberState.text.toInt() * 10)
//            visibleOptionsPost = !visibleOptionsPost
            listkeyword= randomWord(numberState.text.toInt())
        }
        )
        if (visibleOptionsPost) {
            val x = numberState.text.toInt()
            val c = x * 10
            Column {
                Text(text = "$listkeyword")
                val textCoin = if(x != 0) {"$c coins already used. What do you want to do with these keywords?"}else{""}
                Text(text = textCoin)
                Row {
                    ButtonClickItem(desc = "Trash", onClick = {createVM.saveMeme(Meme(
                        username,
                        listkeyword,
                        TRASH
                    ))
                        visibleOptionsPost = !visibleOptionsPost}
                    )
                    ButtonClickItem(desc = "Make it", onClick = {visible2= true})
                }
            }

        }


        if(visible2){
            Text(text="Click to use pictures",modifier = Modifier.clickable { visible3 = !visible3 })
            TextFieldOutlined(desc = "Result",resultState)
            if(memePic != ""){
                val y = x(memePic)
                val v = painterResource(id = y)
                Image(v,contentDescription = "meme pic",modifier= Modifier.size(120.dp))
            }
            ButtonClickItem(desc ="Save",onClick = {createVM.saveMeme(Meme(
                username,
                listkeyword,
                MEME,
                username,
                memePic,
                resultState.text
            ))})

            if(visible3){

                val listPicMeme = listOf(
                    "R.drawable.meme0",
                    "R.drawable.meme1",
                    "R.drawable.meme2",
                    "R.drawable.meme3",
                    "R.drawable.meme4",
                    "R.drawable.meme5",
                    "R.drawable.meme6",
                    "R.drawable.meme7",
                    "R.drawable.meme8",
                    "R.drawable.meme9",
                    "R.drawable.meme10",
                    "R.drawable.meme11",
                    "R.drawable.meme12",
                    "R.drawable.meme13",
                    "R.drawable.meme14",
                    "R.drawable.meme15",
                    "R.drawable.meme16",
                    "R.drawable.meme17",
                    "R.drawable.meme18",
                    "R.drawable.meme19",
                    "R.drawable.meme20",
                )
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    for (i in 0..listPicMeme.size - 1) {
                        val y = x(listPicMeme[i])
                        val v = painterResource(id = y)
                        Image(v, contentDescription = "photo meme", modifier = Modifier
                            .clickable {
                                visible3 = !visible3
                                memePic = listPicMeme[i]
                            }
                            .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                            .size(80.dp),
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center)
                    }
                }
            }
        }
    }

    val uiState= createVM.randomStatus.observeAsState()
    uiState.value?.let {
        val result = it.peekContent()
        when(result.status) {
            Status.SUCCESS -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "You can start now",
                    Toast.LENGTH_SHORT).show()
               if(!visibleOptionsPost) visibleOptionsPost = true
            }
            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "error at screen",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressCardToastItem()
            }
        }
    }
    val uiSave= createVM.transactionStatus.observeAsState()
    uiSave.value?.let {
        val result = it.peekContent()
        when(result.status) {
            Status.SUCCESS -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "Transaction successfully done",
                    Toast.LENGTH_SHORT).show()
            }
            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "error at screen",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressCardToastItem()
            }
        }
    }
}