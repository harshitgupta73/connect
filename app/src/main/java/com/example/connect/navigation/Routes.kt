package com.example.connect.navigation

sealed class Routes(val routes: String){
    object HOME: Routes("home")
    object SEARCH: Routes("search")
    object ADDTHREAD: Routes("addThread")
    object NOTIFICATION: Routes("notification")
    object PROFILE: Routes("profile")
    object LOGIN: Routes("login")
    object SIGNUP: Routes("signup")
    object BOTTOMNAV: Routes("bottomNav")
    object SPLASH: Routes("splash")
    object OTHERUSERS : Routes("otherUsers/{data}")
}