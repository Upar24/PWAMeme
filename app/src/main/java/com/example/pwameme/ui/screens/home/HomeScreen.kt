package com.example.pwameme.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.*
import com.example.pwameme.ui.screens.creatememe.CreateMemeViewModel
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Status

@Composable
fun HomeScreen(AuthVM: AuthViewModel= viewModel(),createVM:CreateMemeViewModel= viewModel()){
        Text("Home Screen Hmm LOL${AuthVM.passwordvm} ${AuthVM.usernamevm} " +
                (AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME)
        )
    val username=AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME

    if(AuthVM.isLoggedIn()){
        AuthVM.usernamevm?.let { AuthVM.passwordvm?.let { it1 -> AuthVM.authenticateApi(it, it1) } }
    }
    var memeList= listOf<Meme>()
    var trashList= listOf<Meme>()
    var leaderList= listOf<User>()
    var visiblefunction by remember { mutableStateOf("")}
    val uiState = createVM.memeStatus.observeAsState()
    uiState.value?.let {
        val result= it.peekContent()
        when(result.status){
            Status.SUCCESS -> {
                memeList = result.data ?: return@let

            }
            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressCardToastItem()
            }
        }
    }
    val trashState = createVM.trashStatus.observeAsState()
    trashState.value?.let{
        val result= it.peekContent()
        when(result.status){
            Status.SUCCESS -> {
                trashList= result.data ?: return@let
            }

            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressCardToastItem()
            }
        }
    }
    val leaderState = createVM.leaderList.observeAsState()
    leaderState.value?.let {
        val result= it.peekContent()
        when(result.status){
            Status.SUCCESS -> {
                leaderList= result.data ?: return@let
            }
            Status.LOADING -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.ERROR -> {
                ProgressCardToastItem()
            }
        }
    }
    Column {
        Row {
            ButtonClickItem(desc = "Load Memes",onClick = {createVM.loadAllMemes()
                visiblefunction = "meme"
            }
            )
            ButtonClickItem(desc = "Load Trash",onClick = {createVM.loadAllTrash()
                visiblefunction = "trash"
            })
            ButtonClickItem(desc = "Leaderboard",onClick = {createVM.loadLeader()
                visiblefunction = "leader"
            })
        }
        if(visiblefunction == "meme")MemeList(meme = memeList)
        if(visiblefunction == "trash") TrashList(meme = trashList)
        if(visiblefunction == "leader") LeaderBoardList(user = leaderList)
    }


}
@Composable
fun MemeList(meme: List<Meme>?) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        meme?.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Column {
                    var like by remember { mutableStateOf(it.liking) }
                    var unlike by remember { mutableStateOf(it.unliking) }
                    var save by remember { mutableStateOf(it.saving) }
                    MemeHeader(meme = it)
                    DividerItem()
                    MemeBody(meme = it)
                    DividerItem()
                    Row(
                        Modifier
                            .padding(4.dp)
                            .fillMaxSize(), Arrangement.SpaceBetween
                    ) {
                        if (like) {
                            Text("liked".plus(it.liked.size.toString()), color = Color.Blue)
                        } else {
                            Text("Like".plus(it.liked.size.toString()))
                        }
                        if (unlike) {
                            Text("Unkliked".plus(it.unliked.size.toString()), color = Color.Red)
                        } else {
                            Text("Unlike".plus(it.liked.size.toString()))
                        }
                        if (save) {
                            Text("Saved".plus(it.saved.size.toString()), color = Color.Yellow)
                        } else {
                            Text("Save".plus(it.saved.size.toString()))
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun TrashList(meme: List<Meme>?) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        meme?.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Column {
                    MemeHeader(meme = it)
                    DividerItem()
                    MemeBody(meme = it)
                    DividerItem()
                }
            }
        }
    }
}
@Composable
fun LeaderBoardList(user: List<User>?){
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ){
        user?.forEach {
            Row(){
                Row {
                    var profile by remember { mutableStateOf(false) }
                    if(profile){
                        ProfileScreen(it.username)
                    }
                    val a = x(it.image)
                    val b = painterResource(id = a)
                    Image(b,contentDescription = "leader photo",
                        Modifier
                            .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                            .size(80.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center)
                    Text(it.username,Modifier.clickable { profile = true})
                }
                Text(it.score.toString())
            }
        }
    }
}