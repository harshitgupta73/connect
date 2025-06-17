package com.example.connect.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.connect.navigation.Routes
import kotlinx.coroutines.delay
import com.example.connect.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Splash(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(R.drawable.connect), contentDescription = null, modifier = Modifier.size(180.dp))

    }

    LaunchedEffect(Unit) {
        delay(2000)

        if(FirebaseAuth.getInstance().currentUser != null)
            navController.navigate(Routes.BOTTOMNAV.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        else
            navController.navigate(Routes.LOGIN.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
    }
}