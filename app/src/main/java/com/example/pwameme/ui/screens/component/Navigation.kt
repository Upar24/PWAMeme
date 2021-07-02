package com.example.pwameme.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pwameme.R
import com.example.pwameme.ui.screens.auth.AuthViewModel
import com.example.pwameme.ui.screens.component.ButtonClickItem
import com.example.pwameme.ui.screens.component.ImageProfileItem
import com.example.pwameme.ui.screens.component.ProfileInfoItem
import com.example.pwameme.ui.theme.Black
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_PASSWORD
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.LOGIN
import com.example.pwameme.util.Constants.LOGOUT
import com.example.pwameme.util.Constants.NO_PASSWORD
import com.example.pwameme.util.Constants.NO_USERNAME

@Composable
fun PWAMemeTopNavigation(
    onIconClick: () -> Unit,
    navController: NavHostController
){

    Row(
        modifier= Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colors.primarySurface),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painterResource(id = R.drawable.list),
                contentDescription = "List Top Navigation",
                modifier = Modifier
                    .clickable(
                        onClick = onIconClick

                    )
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "Meme Keyword Generator",
                color = Black,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    letterSpacing = 0.15.sp
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp, end = 16.dp)
            )
        }
    }
}

@Composable
fun PWAMemeDrawerNavigation(
    modifier: Modifier = Modifier,
    closeDrawerAction: () -> Unit,
    navController: NavHostController,
    items:List<BottomNavigationScreens>
){
    Column(
        modifier= modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ){

        AppdrawerHeader(closeDrawerAction)
        Divider()
        Column (
            modifier= modifier
            .fillMaxSize().padding(20.dp),
            Arrangement.SpaceEvenly,
            Alignment.CenterHorizontally,
        ){
            AppdrawerBody(closeDrawerAction,navController,items)
            Divider()
            AppdrawerFooter(navController,closeDrawerAction)
        }
    }
}
@Composable
fun AppdrawerHeader(closeDrawerAction: () -> Unit
) {
    val AuthVM = hiltViewModel<AuthViewModel>()
    val username = AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colors.primarySurface)
        ) {
            Image(
                painterResource(id = R.drawable.list),
                contentDescription = "List Top Navigation",
                modifier = Modifier
                    .clickable(onClick = closeDrawerAction)
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "PWAMemes",
                color = Black,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp, end = 16.dp)
            )
        }
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = username,Modifier.fillMaxWidth(),
                textAlign= TextAlign.Center,
                style = MaterialTheme.typography.body2)
        }
    }

}
@Composable
fun AppdrawerBody(
    closeDrawerAction: () -> Unit,
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    items.forEach {item ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .clickable {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    closeDrawerAction()
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(painter = painterResource(item.icon), contentDescription = "apa aj",modifier = Modifier.height(30.dp))
            Text(stringResource(item.resourceId),fontSize = 16.sp)
        }
    }
}
@Composable
fun AppdrawerFooter(
    navController: NavHostController,
    closeDrawerAction: () -> Unit
) {

    val AuthVM = hiltViewModel<AuthViewModel>()
    AuthVM.getDesc((if(AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) == NO_USERNAME) LOGIN else LOGOUT))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        val x by AuthVM.desc.observeAsState(if(AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) == NO_USERNAME)LOGIN else LOGOUT)

        ButtonClickItem(
            desc = x,
            onClick = {
                closeDrawerAction()
                if (x == LOGIN) {
                    navController.navigate("LoginRoute") {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                        AuthVM.getDesc((if(AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) == NO_USERNAME) LOGIN else LOGOUT))
                    }
                } else {
                    AuthVM.sharedPref.edit().putString(KEY_LOGGED_IN_USERNAME, NO_USERNAME).apply()
                    AuthVM.sharedPref.edit().putString(KEY_LOGGED_IN_PASSWORD, NO_PASSWORD).apply()
                    navController.navigate("Home")
                    AuthVM.getDesc((if(AuthVM.sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) == NO_USERNAME) LOGIN else LOGOUT))
                }
            }
        )
    }
}



@Composable
fun PWAMemeBottomNavigation(
    navController: NavHostController,
    items:List<BottomNavigationScreens>
){
    BottomNavigation {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute= navBackStackEntry?.destination?.route
        items.forEach{screen ->
            BottomNavigationItem(
                icon= { Image(
                    painterResource(id =screen.icon),
                    contentDescription = screen.route,
                    modifier = Modifier.height(25.dp)
                )
                },
                label={
                    Text(
                        stringResource(id = screen.resourceId),
                        color= Black,
                        fontSize = 16.sp,fontWeight = FontWeight.Bold
                    )
                },
                selected = currentRoute==screen.route,
                alwaysShowLabel= false,
                onClick = {
                    navController.navigate(screen.route){
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it){
                                saveState=true
                            }
                        }
                        launchSingleTop=true
                        restoreState=true
                    }
                },
            )
        }
    }
}




