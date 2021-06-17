package com.example.pwameme.ui.screens.component

import android.app.AlertDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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