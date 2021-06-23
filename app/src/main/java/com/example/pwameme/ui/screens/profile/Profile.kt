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
import com.example.pwameme.R
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.*
import com.example.pwameme.ui.screens.creatememe.CreateMemeViewModel
import com.example.pwameme.ui.screens.profile.ProfileViewModel
import com.example.pwameme.util.Status

@Composable
fun ProfileScreen(username:String) {
    val profileVM = hiltViewModel<ProfileViewModel>()
    val createVM = hiltViewModel<CreateMemeViewModel>()
    val uiState= profileVM.getUserInfo.observeAsState()
    ButtonClickItem(desc = "Click To see",onClick ={profileVM.getUserInfo(username)
        createVM.getUserPost(username)
    } )

    uiState.value?.let {
        when(uiState.value?.status){
            Status.SUCCESS -> {
                UserScreen(uiState.value?.data)
            }
            Status.LOADING -> {
                ProgressCardToastItem()
            }
            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, uiState.value?.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    val postUserState= createVM.userPost.observeAsState()

    postUserState.value?.let {
        val result= it.peekContent()
        when(result.status){
            Status.SUCCESS -> {
                UserPostList(result.data)
            }
            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, uiState.value?.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressCardToastItem()
            }
        }
    }
    Column {
        var lmaoo by remember { mutableStateOf("R.drawable.image0") }
        var visible2 by remember { mutableStateOf(false) }
        Text(text = "Profile Screen")
        ImageProfileItem(
            oom = lmaoo,
            username = "Fina",
            onClick = {}
        )
        Text(text = "Change Picture", modifier = Modifier.clickable { visible2 = !visible2 })
        if (visible2) {
            val listPic = listOf(
                "R.drawable.image0",
                "R.drawable.image1",
                "R.drawable.image2",
                "R.drawable.image3",
                "R.drawable.image4",
                "R.drawable.image5",
                "R.drawable.image6"
            )
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                for (i in 0..listPic.size - 1) {
                    val y = x(listPic[i])
                    val v = painterResource(id = y)
                    Image(v, contentDescription = "photo profile", modifier = Modifier
                        .clickable {
                            visible2 = !visible2
                            lmaoo = listPic[i]
                        }
                        .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                        .size(80.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center)
                }
            }

        }
        Text(text = lmaoo)

    }
}
@Composable
fun UserScreen(user: User?){
    user?.let {
        Row {
            ImageProfileItem(oom = user.image, username = user.username,onClick ={})
            ProfileInfoItem(number = user.score.toString(), desc = "Score")
        }
    }
}
@Composable
fun UserPostList(post: List<Meme>?){
    post?.forEach {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            if (it.type == "meme") {
                Column {
                    var like by remember { mutableStateOf(it.liking) }
                    var unlike by remember { mutableStateOf(it.unliking) }
                    var save by remember { mutableStateOf(it.saving) }
                    MemeHeader(meme =it)
                    DividerItem()
                    MemeBody(meme = it)
                    DividerItem()
                    Row(
                        Modifier
                            .padding(4.dp)
                            .fillMaxSize(),Arrangement.SpaceBetween){
                        if(like){
                            Text("liked".plus(it.liked.size.toString()),color = Color.Blue)
                        }else{
                            Text("Like".plus(it.liked.size.toString()))
                        }
                        if(unlike){
                            Text("Unkliked".plus(it.unliked.size.toString()),color = Color.Red)
                        }else{
                            Text("Unlike".plus(it.liked.size.toString()))
                        }
                        if(save){
                            Text("Saved".plus(it.saved.size.toString()),color = Color.Yellow)
                        }else{
                            Text("Save".plus(it.saved.size.toString()))
                        }
                    }
                }
            }else{
                MemeHeader(meme =it)
                DividerItem()
                MemeBody(meme = it)
                DividerItem()
            }
        }
    }
}


