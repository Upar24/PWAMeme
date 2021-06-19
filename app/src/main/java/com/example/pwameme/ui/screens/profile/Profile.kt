package com.example.pwameme.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pwameme.R
import com.example.pwameme.ui.screens.component.ImageListItem
import com.example.pwameme.ui.screens.component.ImageProfileItem
import com.example.pwameme.ui.screens.component.x

@Composable
fun ProfileScreen() {
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
                        .clickable { visible2 = !visible2
                        lmaoo = listPic[i]}
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


