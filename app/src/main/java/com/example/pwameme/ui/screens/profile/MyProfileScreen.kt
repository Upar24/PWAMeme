package com.example.pwameme.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.*
import com.example.pwameme.ui.screens.profile.ProfileViewModel
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Status

@Composable
fun MyProfileScreen(){
    Text(text = "My Profile Screen")
    val ProfileVM = hiltViewModel<ProfileViewModel>()
    val AuthVM = hiltViewModel<AuthViewModel>()
    val username = AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME

    ProfileVM.getUserInfo(username = username)
    val uiState = ProfileVM.getUserInfo.observeAsState()
    uiState.value?.let {
        val result = it.peekContent()
        when(result.status){
            Status.SUCCESS ->{
                UserInfo(true,result.data!!,ProfileVM,AuthVM)
            }
            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, result.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressBarItem()
            }
        }
    }
}
@Composable
fun UserInfo(visible : Boolean = false, user: User,profileVM:ProfileViewModel,authVM: AuthViewModel){

    var visible1 by remember { mutableStateOf(visible) }
    var visible2 by remember { mutableStateOf(false) }
    val bioState = remember {TextFieldState()}
    val passwordState = remember {TextFieldState()}
    var lmaoo by remember {mutableStateOf("R.drawable.image0")}
    if(visible1){
        Spacer(modifier = Modifier.padding(30.dp))
        Column {
            ImageProfileItem(lmaoo,user.username)
            Text(user.username)
            ProfileInfoItem(number = user.followers.size.toString(), desc = "followers")
            ProfileInfoItem(number = user.following.size.toString(), desc = "following")
            ProfileInfoItem(number = user.score.toString(), desc = "score")
            Text(text = "Bio")
            Text(text = user.bio)
            ButtonClickItem(desc = "Edit",onClick = {visible1 = !visible1})
        }
    }else{
        Column {
            ImageProfileItem(oom = lmaoo, username =user.username)
            Text(text = "Change Picture",modifier = Modifier.clickable{visible2 = true})
            Text("Change Bio")
            TextFieldOutlined(desc = "Bio",bioState)
            Text("Change Password")
            TextFieldOutlined(desc = "New Password",passwordState)
            val userInfo= User(
                username = user.username,
                password = passwordState.text,
                following = user.following,
                followers = user.followers,
                image = lmaoo,
                bio = bioState.text,
                score = user.score,
                _id = user._id)
            ButtonClickItem(desc = "save", onClick = {
                profileVM.UpdateUser(userInfo)
            })
        }
    }
    if(visible2){
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
                    .clickable { visible2 = !visible2
                        lmaoo = listPic[i]}
                    .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                    .size(80.dp),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center)
            }
        }

    }
    val uiState = profileVM.userInfoUpdate.observeAsState()
    uiState.value?.let {
        val result = it.peekContent()
        when(result.status){
            Status.SUCCESS ->{
                Toast.makeText(
                    LocalContext.current, result.message ?: "Info successfully updated",
                    Toast.LENGTH_SHORT).show()
                authVM.sharedPref.edit().putString("pp",lmaoo).apply()
            }
            Status.ERROR ->{
                Toast.makeText(
                    LocalContext.current, result.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING ->{
                ProgressBarItem()
            }
        }
    }
}
 