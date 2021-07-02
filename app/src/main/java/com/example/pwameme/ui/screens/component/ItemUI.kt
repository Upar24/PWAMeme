package com.example.pwameme.ui.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pwameme.R
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.ui.theme.Black
import com.example.pwameme.ui.theme.Pink

class TextFieldState(init:String=""){
    var text : String by mutableStateOf(init)
}
@Composable
fun MemeBody(meme:Meme){
    Column(
        Modifier
            .padding(7.dp)
            .fillMaxSize(),Arrangement.Center,Alignment.CenterHorizontally) {
        Text(meme.keyword.toString(),
            Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Green, Color.Yellow
                        )
                    ), shape = CircleShape
                )
                .padding(5.dp),fontSize = 18.sp)
        meme.descMeme?.let {
            Text(
                it,
                Modifier
                    .background(
                        Brush.verticalGradient(colors = listOf(Color.Yellow, Color.Yellow)),
                        shape = CircleShape
                    )
                    .padding(12.dp),fontSize = 18.sp)
        }
        if(meme.imageMeme != null || meme.imageMeme != ""){
            val imageMeme = meme.imageMeme?.let { x(it) }
            val image = imageMeme?.let { painterResource(id = it) }
            if (image != null) {
                Image(painter = image, contentDescription = meme.imageMeme,
                    Modifier
                        .padding(start = 5.dp)
                        .size(200.dp))
            }
        }
    }
}
@Composable
fun MemeHeader(meme: Meme,navController: NavHostController){
        Column(Modifier.padding(5.dp)) {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                Arrangement.SpaceBetween
            ) {
                Column {
                    Text(meme.usernameAuthor,
                        Modifier.clickable{navController.navigate("UserProfileScreenRoute/${meme.usernameAuthor}")},
                        fontSize= 20.sp
                    )
                    Text("Meme's Author")
                }
                Column {
                    Text(meme.usernameKeyword,
                        Modifier.clickable{navController.navigate("UserProfileScreenRoute/${meme.usernameKeyword}")},
                        fontSize= 20.sp)
                    Text("Keyword's Author")
                }
            }
        }
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
            color = Black,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = text2,
            color = Black,
            style = MaterialTheme.typography.subtitle1,
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
fun ButtonClickItem(desc: String, onClick: () -> Unit, style: TextStyle = MaterialTheme.typography.button) {
    Button(
        onClick =  onClick,colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
    ){
        Text(desc, style=style)
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
            CircularProgressIndicator(color = Pink)
            Spacer(Modifier.size(10.dp))
            Text(
                text="Please wait..",style = MaterialTheme.typography.body2
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
            backgroundColor = MaterialTheme.colors.primaryVariant
        ){
            ProgressBarItem()
        }
    }

}
@Composable
fun AlertDialogItem(title:String,text:String,onClick: () -> Unit){
    var openDialog by remember { mutableStateOf(true)}
    if(openDialog){
        AlertDialog(
            onDismissRequest={openDialog = false},
            title = { Text(text = title) },text = { Text(text= text) },confirmButton = {
                ButtonClickItem(desc = "Yes",onClick = onClick)
            },dismissButton = {
                Button(
                    onClick = {openDialog= false}
                ){
                    Text("No")
                }
            }
        )
    }

}
@Composable
fun ImageProfileItem(oom:String,username:String){
    val x = x(oom)!!
    val lam = painterResource(id = x)
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        lam?.let {
            Image(
                it,modifier = Modifier
                    .padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
                    .size(80.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,contentDescription = "Photo Profile",
            )
        }
        Text(
            text=username,
            style=TextStyle(fontSize = 20.sp),
            modifier=Modifier.padding(bottom=5.dp),
            color= MaterialTheme.colors.primaryVariant
        )
    }
}

fun x(string:String): Int? {
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
    return if(string=="")null else lmao
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