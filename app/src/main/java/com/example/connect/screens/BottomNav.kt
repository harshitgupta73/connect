package com.example.connect.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.connect.model.BottomNavItem
import com.example.connect.navigation.Routes

@Composable
fun BottomNav(navController: NavHostController) {

    val navController1 = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomBar(navController1)}
    ) {
        NavHost(
            navController = navController1,
            startDestination = Routes.HOME.routes,
            modifier = Modifier.padding(it)
        ) {
            composable(Routes.HOME.routes){
                Home(navController)
            }
            composable(Routes.NOTIFICATION.routes){
                Notification()
            }
            composable(Routes.ADDTHREAD.routes){

                AddThreads(navController1)
            }
            composable(Routes.SEARCH.routes){
                Search(navController)
            }
            composable(Routes.PROFILE.routes){
                Profile(navController)
            }
        }
    }
}

@Composable
fun MyBottomBar(navController1: NavHostController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem("Home", Routes.HOME.routes, Icons.Default.Home),
        BottomNavItem("Search", Routes.SEARCH.routes, Icons.Default.Search),
        BottomNavItem("Add Thread", Routes.ADDTHREAD.routes, Icons.Default.Add),
        BottomNavItem("Notification", Routes.NOTIFICATION.routes, Icons.Default.Notifications),
        BottomNavItem("Profile", Routes.PROFILE.routes, Icons.Default.Person),
    )

    BottomAppBar {
        list.forEach {
            val selected : Boolean = it.route == backStackEntry?.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController1.navigate(it.route){
                        popUpTo(navController1.graph.findStartDestination().id){
                            saveState=true
                        }
                        launchSingleTop=true
                    }
                },
                icon = {
                    Icon(imageVector = it.icon, contentDescription = null)
                }
            )


        }
    }
}