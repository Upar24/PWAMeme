package com.example.pwameme.ui.screens.component

import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pwameme.R
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.ui.screens.ProfileScreen

class TextFieldState{
    var text : String by mutableStateOf("")
}
@Composable
fun MemeHeader(meme: Meme){
    var profile by remember { mutableStateOf(false) }
    var username by remember {mutableStateOf("")}
    if(profile){
        ProfileScreen(username)
    }
    Column {
        Row(
            Modifier
                .padding(4.dp)
                .fillMaxSize(),Arrangement.SpaceBetween){
            Column{
                Text(meme.usernameAuthor,Modifier.clickable { profile= true
                    username = meme.usernameAuthor })
                Text("Meme's Author")
            }
            Column {
                Text(meme.usernameKeyword,Modifier.clickable { profile= true
                    username = meme.usernameKeyword
                })
                Text("Keyword's Author")
            }
        }
    }
}
@Composable
fun MemeBody(meme:Meme){
    Column(Modifier.fillMaxSize(),Arrangement.Center,Alignment.CenterHorizontally) {
        Text(meme.keyword.toString())
        Text(meme.descMeme)
        if(meme.imageMeme != null || meme.imageMeme != ""){
            val imageMeme = meme.imageMeme?.let { x(it) }
            val image = imageMeme?.let { painterResource(id = it) }
            if (image != null) {
                Image(painter = image, contentDescription = meme.imageMeme)
            }
        }
    }
}
@Preview
@Composable
fun y (){
    MemeHeader(Meme(
        "Upar", listOf("lmao","lal","live","wins"),
        "meme","Fina","R.drawable.meme4","lol will lol live wins as always",
        listOf("fina","upar","random"),listOf("haters"), listOf(), listOf(),4,false,true,true,"lamoooooo"
    ))
}
@Composable
fun DividerItem(){
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = .2f),
        modifier = Modifier.padding(start = 7.dp, end = 7.dp, bottom = 7.dp)
    )
}
@Composable
fun ProfileInfoItem(number:String,desc:String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = number,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 0.15.sp,
                textAlign = TextAlign.Center
            ),
        )
        Text(
            text=desc,
            style= TextStyle(fontSize = 16.sp)
        )
    }
}
@Composable
fun SwitchTOLoginOrRegisterTexts(
    modifier: Modifier,
    text1: String,
    text2: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text1,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = text2,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.clickable { onClick() }
        )
    }
}
@Composable
fun TextFieldItem(
    modifier:Modifier,
    state: TextFieldState = remember{ TextFieldState()},
    key:KeyboardOptions
){
    TextField(
        value = state.text,
        onValueChange = {
            state.text = it
        },
        modifier = modifier,
        keyboardOptions = key
    )
}
@Composable
fun TextFieldOutlined(desc:String,state: TextFieldState = remember {TextFieldState()}){
    OutlinedTextField(
        label={Text(text=desc)},
        value =state.text,
        onValueChange = {
            state.text = it
        }
    )
}
@Composable
fun ButtonClickItem(desc: String,onClick: () -> Unit) {
    Button(
        onClick =  onClick
    ){
        Text(desc)
    }
}
@Composable
fun ProgressBarItem(){
    Row(
        verticalAlignment =Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()
            Spacer(Modifier.size(10.dp))
            Text(
                text="Please wait.."
            )
        }

    }
}
@Composable
fun ProgressCardToastItem(){
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),

        Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp),
            shape= RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.secondary
        ){
            ProgressBarItem()
        }
    }

}
@Composable
fun CardToastItem(desc:String){
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),

        Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp),
            shape= RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.secondary
        ){
            Text(desc,
                modifier = Modifier.padding(5.dp),
                textAlign = TextAlign.Center
            )
        }
    }

}
@Composable
fun AlertDialogItem(title:String,text:String,onClick: () -> Unit){
    AlertDialog(
        onDismissRequest={},
                title = { Text(text = title) },text = { Text(text= text) },confirmButton = {
            Button(
                    onClick =  onClick
                ){
                    Text("Yes")
                }
        },dismissButton = {}
    )
}
@Composable
fun ImageProfileItem(oom:String,username:String,onClick: () -> Unit){
    val x = x(oom)
    val lam = painterResource(x)
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Image(lam,modifier = Modifier
            .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
            .size(80.dp),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,contentDescription = "Photo Profile",
        )
        Text(
            text=username,//sharedPref
            style=TextStyle(fontSize = 20.sp),
            modifier=Modifier.padding(bottom=5.dp),
            color= MaterialTheme.colors.primaryVariant
        )
    }
}
@Composable
fun ImageListItem(oom: String, onClick: () -> Unit) {
    val x = x(oom)
    val lam = painterResource(x)
    Image(lam,contentDescription = null,modifier = Modifier
        .clickable { onClick }
        .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
        .size(80.dp),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center)
    }

fun x(string:String):Int{
    var lmao = 0
    when(string){
        "R.drawable.image0" -> lmao = R.drawable.image0
        "R.drawable.image1" -> lmao = R.drawable.image1
        "R.drawable.image2" -> lmao = R.drawable.image2
        "R.drawable.image3" -> lmao = R.drawable.image3
        "R.drawable.image4" -> lmao = R.drawable.image4
        "R.drawable.image5" -> lmao = R.drawable.image5
        "R.drawable.image6" -> lmao = R.drawable.image6
        "R.drawable.meme0"  -> lmao = R.drawable.meme0
        "R.drawable.meme1"  -> lmao = R.drawable.meme1
        "R.drawable.meme2"  -> lmao = R.drawable.meme2
        "R.drawable.meme3"  -> lmao = R.drawable.meme3
        "R.drawable.meme4"  -> lmao = R.drawable.meme4
        "R.drawable.meme5"  -> lmao = R.drawable.meme5
        "R.drawable.meme6"  -> lmao = R.drawable.meme6
        "R.drawable.meme7"  -> lmao = R.drawable.meme7
        "R.drawable.meme8"  -> lmao = R.drawable.meme8
        "R.drawable.meme9"  -> lmao = R.drawable.meme9
        "R.drawable.meme10" -> lmao = R.drawable.meme10
        "R.drawable.meme11" -> lmao = R.drawable.meme11
        "R.drawable.meme12" -> lmao = R.drawable.meme12
        "R.drawable.meme13" -> lmao = R.drawable.meme13
        "R.drawable.meme14" -> lmao = R.drawable.meme14
        "R.drawable.meme15" -> lmao = R.drawable.meme15
        "R.drawable.meme16" -> lmao = R.drawable.meme16
        "R.drawable.meme17" -> lmao = R.drawable.meme17
        "R.drawable.meme18" -> lmao = R.drawable.meme18
        "R.drawable.meme19" -> lmao = R.drawable.meme19
        "R.drawable.meme20" -> lmao = R.drawable.meme20
    }
    return lmao
}
fun randomWord(number:Int): MutableList<String> {
    val lists = listOf<String>(
        "lmao", "lol", "laugh", "dang", "lal"
    )
    val listkeyword = mutableListOf<String>()
    for (i in 1..number) {
        val y = lists.random()
        listkeyword.add(y)
    }
    return listkeyword
}