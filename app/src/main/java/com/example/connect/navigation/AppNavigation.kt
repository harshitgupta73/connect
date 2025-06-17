package com.example.connect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.connect.screens.AddThreads
import com.example.connect.screens.BottomNav
import com.example.connect.screens.Home
import com.example.connect.screens.Login
import com.example.connect.screens.Notification
import com.example.connect.screens.OthersProfile
import com.example.connect.screens.Profile
import com.example.connect.screens.Search
import com.example.connect.screens.SignUp
import com.example.connect.screens.Splash

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Routes.SPLASH.routes) {

        composable(Routes.SPLASH.routes){
            Splash(navController)
        }
        composable(Routes.HOME.routes){
            Home(navController)
        }
        composable(Routes.NOTIFICATION.routes){
            Notification()
        }
        composable(Routes.ADDTHREAD.routes){

            AddThreads(navController)
        }
        composable(Routes.SEARCH.routes){
            Search(navController)
        }
        composable(Routes.BOTTOMNAV.routes){
            BottomNav(navController)
        }
        composable(Routes.LOGIN.routes){
            Login(navController)
        }
        composable(Routes.SIGNUP.routes){
            SignUp(navController)
        }
        composable(Routes.PROFILE.routes){
            Profile(navController)
        }
        composable(Routes.OTHERUSERS.routes){
            val data = it.arguments!!.getString("data")
            OthersProfile(navController,data!!)
        }

    }


}

