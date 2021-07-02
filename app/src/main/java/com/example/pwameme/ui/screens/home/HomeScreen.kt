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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.*
import com.example.pwameme.ui.screens.creatememe.CreateMemeViewModel
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.MEME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Status

@Composable
fun HomeScreen(
    navController: NavHostController,AuthVM: AuthViewModel= viewModel(),createVM:CreateMemeViewModel= viewModel()){

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
    Column (Modifier.padding(top=3.dp,bottom = 100.dp)){
        Row (Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            ButtonClickItem(
                desc = "All Memes", onClick = {createVM.loadAllMemes()
                visiblefunction = "meme"
            }, style = if(visiblefunction == "meme") MaterialTheme.typography.body1 else MaterialTheme.typography.button
            )
            ButtonClickItem(desc = "All Trash", onClick = {createVM.loadAllTrash()
                visiblefunction = "trash"
            }, style = if(visiblefunction == "trash") MaterialTheme.typography.body1 else MaterialTheme.typography.button)
            ButtonClickItem(desc = "Leaderboard", onClick = {createVM.loadLeader()
                visiblefunction = "leader"
            }, style = if(visiblefunction == "leader") MaterialTheme.typography.body1 else MaterialTheme.typography.button)
        }
        if(visiblefunction == "meme")MemeList(username,memeList,navController)
        if(visiblefunction == "trash") TrashList(meme = trashList, navController)
        if(visiblefunction == "leader") LeaderBoardList(user = leaderList,navController)
    }


}
@Composable
fun MemeList(username:String,meme: List<Meme>?,navController: NavHostController) {
    val createVM = hiltViewModel<CreateMemeViewModel>()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        meme?.forEach {meme ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Column {
                    MemeHeader(meme = meme, navController)
                    DividerItem()
                    MemeBody(meme = meme)
                    DividerItem()
                    Row(
                        Modifier
                            .padding(4.dp)
                            .fillMaxSize(), Arrangement.SpaceBetween
                    ) {


                        var like by remember { mutableStateOf(if(username in meme.liked)true else false) }
                        var save by remember { mutableStateOf(if(username in meme.saved) true else false) }
                        var countLiked by remember{ mutableStateOf(meme.liked.size)}
                        var countSaved by remember { mutableStateOf(meme.saved.size)}
                        var likeState= createVM.likeStatus.observeAsState()
                        likeState.value?.let {
                            val result = it.peekContent()
                            when (result.status){
                                Status.SUCCESS -> {
                                    like= result.data?.successful!!
                                    countLiked= result.data?.message.toInt()
                                    Toast.makeText(LocalContext.current,  "Successfully",
                                        Toast.LENGTH_SHORT).show()
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
                        var saveState= createVM.saveStatus.observeAsState()
                        saveState.value?.let {
                            val result = it.peekContent()
                            when (result.status){
                                Status.SUCCESS -> {
                                    save = result.data?.successful!!
                                    countSaved=result.data?.message!!.toInt()
                                    Toast.makeText(LocalContext.current,  "Successfully",
                                        Toast.LENGTH_SHORT).show()
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
                        if (like) {
                            Text("Liked  $countLiked",Modifier.clickable { createVM.toggleLike(username,meme)},Color.Blue)
                        } else {
                            Text("Like $countLiked",Modifier.clickable { createVM.toggleLike(username,meme)})
                        }
                        if (save) {

                            Text("Saved $countSaved",Modifier.clickable { createVM.toggleSave(username,meme)},Color.Yellow)
                        } else {
                            Text("Save $countSaved",Modifier.clickable { createVM.toggleSave(username,meme)})
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun TrashList(meme: List<Meme>?,navController: NavHostController,createVM:CreateMemeViewModel= viewModel()) {
    val AuthVM = hiltViewModel<AuthViewModel>()
    val username=AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        meme?.forEach {trash->
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Column(Modifier.padding(12.dp)) {

                    var makeMemeVisible by remember { mutableStateOf(false)}
                    var imageVisible by remember { mutableStateOf(false)}
                    val resultState = remember {TextFieldState()}
                    var memePic by remember { mutableStateOf("") }
                    MemeHeader(meme = trash, navController  )
                    DividerItem()
                    MemeBody(meme = trash)
                    DividerItem()
                    ButtonClickItem(desc = "Take it", onClick ={makeMemeVisible = true})
                    if(makeMemeVisible == true){
                        Text(text="Click to use pictures",modifier = Modifier.clickable { imageVisible = !imageVisible })
                        TextFieldOutlined(desc = "Result",resultState)
                        if(memePic != ""){
                            val y = x(memePic)
                            val v = y?.let { painterResource(id = it) }
                            v?.let { Image(it,contentDescription = "meme pic",modifier= Modifier.size(120.dp)) }
                        }
                        ButtonClickItem(desc ="Save", onClick = {createVM.saveMeme(username,Meme(
                            trash.usernameKeyword,
                            trash.keyword,
                            MEME,
                            username,
                            memePic,
                            resultState.text,
                            _id= trash._id
                        ))})

                        if(imageVisible){

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
                                    val v = y?.let { painterResource(id = it) }
                                    v?.let {
                                        Image(it, contentDescription = "photo meme", modifier = Modifier
                                            .clickable {
                                                imageVisible = !imageVisible
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
                }
            }
        }
    }
}
@Composable
fun LeaderBoardList(user: List<User>?,navController: NavHostController){
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ){
        user?.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp),
                shape= RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.primaryVariant
            ){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ){
                    val a = x(it.image)!!
                    val b = painterResource(id = a)
                    Image(b,contentDescription = "leader photo",
                        Modifier
                            .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                            .size(80.dp),
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center)

                    Text(it.username,Modifier.clickable {navController.navigate("UserProfileScreenRoute/${it.username}")},style = MaterialTheme.typography.body2,)
                    Text(it.score.toString(),style = MaterialTheme.typography.subtitle1)
                }
            }
        }
    }
}