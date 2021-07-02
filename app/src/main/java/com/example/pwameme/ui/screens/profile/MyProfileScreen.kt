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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    val ProfileVM = hiltViewModel<ProfileViewModel>()
    val AuthVM = hiltViewModel<AuthViewModel>()
    val username = AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 5.dp,bottom = 100.dp)) {
        ButtonClickItem(desc = "Click to see",onClick = {ProfileVM.getUserInfo(username)})
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
                    ProgressCardToastItem()
                }
            }
        }
    }

}
@Composable
fun UserInfo(visible : Boolean = false, user: User,profileVM:ProfileViewModel,authVM: AuthViewModel){

    var visible1 by remember { mutableStateOf(visible) }
    var visiblePic by remember { mutableStateOf(false) }
    val bioState = remember {TextFieldState()}
    val passwordState = remember {TextFieldState()}
    var lmaoo by remember {mutableStateOf(user.image)}
    if(visible1){
        Spacer(modifier = Modifier.padding(30.dp))
        Column (
            Modifier
                .fillMaxWidth()
                .padding(5.dp)){
            ImageProfileItem(lmaoo,user.username)
            Text(user.username)
            ProfileInfoItem(number = user.score.toString(), desc = "score")
            Text(text = "Bio",style = MaterialTheme.typography.subtitle1)
            Text(text = user.bio,style = MaterialTheme.typography.body2)
            ButtonClickItem(desc = "Edit", onClick = {visible1 = !visible1})
        }
    }else{
        Column {
            ImageProfileItem(oom = lmaoo, username =user.username)
            Text(text = "Change Picture",style = MaterialTheme.typography.body2,modifier = Modifier.clickable{visiblePic = true})
            Text("Change Bio",style = MaterialTheme.typography.subtitle1)
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
    if(visiblePic){
        val listPic = listOf(
            "R.drawable.image0",
            "R.drawable.image1",
            "R.drawable.image2",
            "R.drawable.image3",
            "R.drawable.image4",
            "R.drawable.image5",
            "R.drawable.image6"
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp),
            shape= RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ){
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                for (i in 0..listPic.size - 1) {
                    val y = x(listPic[i])!!
                    val v = painterResource(id = y)
                    Image(v, contentDescription = "photo profile", modifier = Modifier
                        .clickable {
                            visiblePic = !visiblePic
                            lmaoo = listPic[i]
                        }
                        .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                        .size(80.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center)
                }
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
                visible1=true
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
 