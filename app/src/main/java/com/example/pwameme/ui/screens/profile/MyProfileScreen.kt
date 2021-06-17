package com.example.pwameme.ui.screens

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import com.example.pwameme.R
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.ProfileInfoItem
import com.example.pwameme.ui.screens.component.ProgressBarItem
import com.example.pwameme.ui.screens.profile.ProfileViewModel
import com.example.pwameme.util.Constants
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Status

@Composable
fun MyProfileScreen(){
    Text(text = "My Profile Screen")
    val ProfileVM = hiltViewModel<ProfileViewModel>()
    val AuthVM = hiltViewModel<AuthViewModel>()
    val username = AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME

    val uiState = ProfileVM.getUserInfo.observeAsState()
    ProfileVM.getUserInfo(username)
    uiState.value?.let {
        when(it.status){
            Status.SUCCESS ->{
                UserInfo(true,it.data!!)
            }
            Status.ERROR -> {
                Toast.makeText(
                    LocalContext.current, uiState.value?.message ?: "An unknown error occured",
                    Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> {
                ProgressBarItem()
            }
        }
    }
}
@Composable
fun UserInfo(visible : Boolean = false, user: User){
    if(visible){
        Spacer(modifier = Modifier.padding(30.dp))
        Column {
            Image(painterResource(R.drawable.image), contentDescription ="apa aj" )
            Text(user.username)
            ProfileInfoItem(number = user.followers.size.toString(), desc = "followers")
            ProfileInfoItem(number = user.following.size.toString(), desc = "following")
            ProfileInfoItem(number = user.score.toString(), desc = "score")
            Text(text = "Bio")
            Text(text = user.bio)
        }
    }
}
@Preview()
@Composable
fun x (){
    UserInfo(true, user = User(
        username = "Fina",
        password = "1234",
        followers = listOf("Fina","Alfionita","Sitanggang"),
        following = listOf("Dea"),
        image = "R.drawable.image",
        bio = "I can hear your heart beating in the darkness",
        score =  240,
       _id =  "kjkjkjkjk"
    ))
}