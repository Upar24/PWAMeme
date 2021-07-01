package com.example.pwameme.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pwameme.R
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.*
import com.example.pwameme.ui.screens.creatememe.CreateMemeViewModel
import com.example.pwameme.ui.screens.profile.ProfileViewModel
import com.example.pwameme.util.Constants
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Status

@Composable
fun ProfileScreen(navController: NavHostController) {
    val profileVM = hiltViewModel<ProfileViewModel>()
    val createVM = hiltViewModel<CreateMemeViewModel>()
    val authVM = hiltViewModel<AuthViewModel>()
    val uiState= profileVM.getUserInfo.observeAsState()
    val username=authVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME
    ) ?: NO_USERNAME
    Column {
        ButtonClickItem(desc = "Click To see",onClick ={profileVM.getUserInfo(username)
            createVM.getUserMeme(username)
        } )
        var memeList= listOf<Meme>()
        var trashList= listOf<Meme>()
        var visiblefunction by remember { mutableStateOf("")}
        uiState.value?.let {
            val result = it.peekContent()
            when(result.status){
                Status.SUCCESS -> {
                    UserScreen(result.data)
                }
                Status.LOADING -> {
                    ProgressCardToastItem()
                }
                Status.ERROR -> {
                    Toast.makeText(
                        LocalContext.current, result.message ?: "An unknown error occured",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        val memeUserState= createVM.userMeme.observeAsState()
        memeUserState.value?.let {
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
        val trashUserState= createVM.userTrash.observeAsState()
        trashUserState.value?.let {
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
        ButtonClickItem(desc = "Load all $username meme",onClick = { createVM.getUserMeme(username)
            visiblefunction = "meme"})
        ButtonClickItem(desc = "Load all $username trash",onClick = { createVM.getUserTrash(username)
            visiblefunction = "trash"
        })
        ButtonClickItem(desc = username,onClick = {})
        if(visiblefunction == "meme")MemeList(meme = memeList,navController)
        if(visiblefunction == "trash") TrashList(meme = trashList, navController)
    }
}
@Composable
fun OtherProfileUsername(navController: NavHostController, username: String){
    val profileVM = hiltViewModel<ProfileViewModel>()
    val createVM = hiltViewModel<CreateMemeViewModel>()
    val uiState= profileVM.getUserInfo.observeAsState()

        Column {
            var memeList= listOf<Meme>()
            var trashList= listOf<Meme>()
            var visiblefunction by remember { mutableStateOf("")}
            uiState.value?.let {
                val result = it.peekContent()
                when(result.status){
                    Status.SUCCESS -> {
                        UserScreen(result.data)
                    }
                    Status.LOADING -> {
                        ProgressCardToastItem()
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            LocalContext.current, result.message ?: "An unknown error occured",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            val memeUserState= createVM.userMeme.observeAsState()
            memeUserState.value?.let {
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
            val trashUserState= createVM.userTrash.observeAsState()
            trashUserState.value?.let {
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
            ButtonClickItem(desc = "Click To see",onClick ={profileVM.getUserInfo(username)})
            ButtonClickItem(desc = "Load all $username meme",onClick = { createVM.getUserMeme(username)
                visiblefunction = "meme"})
            ButtonClickItem(desc = "Load all $username trash",onClick = { createVM.getUserTrash(username)
                visiblefunction = "trash"
            })
            ButtonClickItem(desc = username,onClick = {})

            if(visiblefunction == "meme")MemeList(meme = memeList,navController)
            if(visiblefunction == "trash") TrashList(meme = trashList, navController)
        }
    }

@Composable
fun UserScreen(user: User?){
    user?.let {
        Row (Modifier.fillMaxWidth(),Arrangement.SpaceAround){
            ImageProfileItem(oom = user.image, username = user.username)
            ProfileInfoItem(number = user.score.toString(), desc = "Score")
        }
    }
}
