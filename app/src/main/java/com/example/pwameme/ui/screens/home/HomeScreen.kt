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
import com.example.pwameme.util.Constants
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.MEME
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Constants.TRASH
import com.example.pwameme.util.Status

@Composable
fun HomeScreen(
    navController: NavHostController,AuthVM: AuthViewModel= viewModel(),createVM:CreateMemeViewModel= viewModel()){
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
        if(visiblefunction == "meme")MemeList(meme = memeList,navController)
        if(visiblefunction == "trash") TrashList(meme = trashList, navController)
        if(visiblefunction == "leader") LeaderBoardList(user = leaderList,navController)
    }


}
@Composable
fun MemeList(meme: List<Meme>?,navController: NavHostController) {
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


                        var like by remember { mutableStateOf(meme.liking) }
//            var unlike by remember { mutableStateOf(it.unliking) }
                        var save by remember { mutableStateOf(meme.saving) }
                        var likeState= createVM.likeStatus.observeAsState()
                        likeState.value?.let {
                            val result = it.peekContent()
                            when (result.status){
                                Status.SUCCESS -> {
                                    like= result.data!!
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
                                    save = result.data!!
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
                            ButtonClickItem(desc = "Liked".plus((meme.liked.size ).toString()),onClick={createVM.toggleLike(meme)})
                        } else {
                            ButtonClickItem(desc = "Like".plus((meme.liked.size).toString()),onClick= {createVM.toggleLike(meme)})
                        }
                        if (save) {
                            ButtonClickItem(desc = "Saved".plus((meme.saved.size).toString()),onClick = {createVM.toggleSave(meme)})
                        } else {
                            ButtonClickItem(desc = "Save".plus((meme.saved.size).toString()),onClick = {createVM.toggleSave(meme)})
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
                    ButtonClickItem(desc = "Take it",onClick ={makeMemeVisible = true})
                    if(makeMemeVisible == true){
                        Text(text="Click to use pictures",modifier = Modifier.clickable { imageVisible = !imageVisible })
                        TextFieldOutlined(desc = "Result",resultState)
                        if(memePic != ""){
                            val y = x(memePic)
                            val v = painterResource(id = y)
                            Image(v,contentDescription = "meme pic",modifier= Modifier.size(120.dp))
                        }
                        ButtonClickItem(desc ="Save",onClick = {createVM.saveMeme(Meme(
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
                                    val v = painterResource(id = y)
                                    Image(v, contentDescription = "photo meme", modifier = Modifier
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
@Composable
fun LeaderBoardList(user: List<User>?,navController: NavHostController){
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ){
        user?.forEach {
            Row(){
                Row {
                    val a = x(it.image)
                    val b = painterResource(id = a)
                    Image(b,contentDescription = "leader photo",
                        Modifier
                            .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                            .size(80.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center)
                    Text(it.username,Modifier.clickable {navController.navigate("UserProfileScreenRoute/${it.username}")})
                }
                Text(it.score.toString())
            }
        }
    }
}