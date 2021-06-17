package com.example.pwameme.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pwameme.R
import com.example.pwameme.ui.screens.auth.AuthViewModel
import kotlinx.coroutines.launch

sealed class BottomNavigationScreens(
    val route:String,
    @StringRes val resourceId:Int,
    val icon: Int
){
    object Home:BottomNavigationScreens("Home", R.string.home_screen_route, R.drawable.home)
    object Add:BottomNavigationScreens("Add",R.string.add_screen_route, R.drawable.add)
    object Profile:BottomNavigationScreens("Profile",R.string.profile_screen_route, R.drawable.profile)
    object Search:BottomNavigationScreens("Search",R.string.search_screen_route, R.drawable.search)
    object MyProfile:BottomNavigationScreens("MyProfile",R.string.myprofile_screen_route, R.drawable.profile)
}
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope= rememberCoroutineScope()
    val bottomNavigationItems= listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Add,
        BottomNavigationScreens.Profile
    )
    val drawerNavigationItems= listOf(
        BottomNavigationScreens.MyProfile
    )

    Scaffold (
        topBar={
            PWAMemeTopNavigation(
                onIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                navController
            )
        },
        scaffoldState=scaffoldState,
        drawerContent={
            PWAMemeDrawerNavigation (
                closeDrawerAction = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                },
                navController =navController,
                items = drawerNavigationItems,
            )


        },
        bottomBar ={
            PWAMemeBottomNavigation(navController , bottomNavigationItems )
        }
    ){
        MainScreenNavigationConfiguration(navController)
    }
}

@Composable
fun MainScreenNavigationConfiguration(
    navController: NavHostController
){
    NavHost(navController, startDestination = BottomNavigationScreens.Home.route){
        composable(BottomNavigationScreens.Home.route){
            val authVM = hiltViewModel<AuthViewModel>()
            HomeScreen()
        }
        composable(BottomNavigationScreens.Add.route){
            val authVM = hiltViewModel<AuthViewModel>()
            AddScreen(navController)
        }
        composable(BottomNavigationScreens.Profile.route){
            ProfileScreen()
        }
        composable(BottomNavigationScreens.Search.route){
            SearchScreen()
        }
        composable(BottomNavigationScreens.MyProfile.route){
            MyProfileScreen()
        }
        composable("LoginRoute"){
            val authVM = hiltViewModel<AuthViewModel>()
            LoginScreen(navController,authVM)
        }
        composable("RegisterRoute"){
            val authVM = hiltViewModel<AuthViewModel>()
            RegisterScreen(navController,authVM)
        }
    }
}
