package com.example.pwameme.ui.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pwameme.R

class TextFieldState{
    var text : String by mutableStateOf("")
}
@Composable
fun Divider(){
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
        verticalAlignment =Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
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
    Image(lam,contentDescription = null,modifier = Modifier.clickable { onClick }.padding(start = 14.dp, top = 4.dp, bottom = 4.dp)
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
    }
    return lmao
}