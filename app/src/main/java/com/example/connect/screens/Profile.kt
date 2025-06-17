package com.example.connect.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.connect.item_view.ThreadItem
import com.example.connect.model.UserModel
import com.example.connect.navigation.Routes
import com.example.connect.utils.SharedPref
import com.example.connect.viewModel.AuthViewModel
import com.example.connect.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(navController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val followerList by userViewModel.followersList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    val user = UserModel(
        name = SharedPref.getName(context),
        userName = SharedPref.getUserName(context),
        imageUri = SharedPref.getImageUrl(context)
    )



    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            navController.navigate(Routes.LOGIN.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(Unit){
        userViewModel.fetchThreads(FirebaseAuth.getInstance().currentUser!!.uid)
        userViewModel.getFollowers(FirebaseAuth.getInstance().currentUser!!.uid)
        userViewModel.getFollowing(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp )
    ) {
        items(1){
            Column () {
                Row (
                    modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column {
                        Text(
                            SharedPref.getName(context), style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                            )
                        )
                        Spacer(modifier=Modifier.height(4.dp))
                        Text(
                            SharedPref.getUserName(context), style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier=Modifier.height(16.dp))
                        Text(
                            SharedPref.getBio(context), style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier=Modifier.height(8.dp))
                        Text(
                            "${followerList?.size} Followers", style = TextStyle(
                                fontWeight = FontWeight.ExtraLight,
                                fontSize = 14.sp
                            )
                        )
                        Spacer(modifier=Modifier.height(4.dp))
                        Text(
                            "${followingList?.size} Following", style = TextStyle(
                                fontWeight = FontWeight.ExtraLight,
                                fontSize = 14.sp
                            )
                        )
                    }
                    Image(
                        painter = rememberAsyncImagePainter(model = SharedPref.getImageUrl(context)),
                        contentDescription = "profile image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier=Modifier.height(8.dp))
                Button(onClick = {
                    authViewModel.signOut()
                }) {
                    Text("LogOut")
                }
                Spacer(modifier=Modifier.height(16.dp))

                Text(
                    "Threads", style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier=Modifier.height(16.dp))

            }
        }
        items(threads ?: emptyList()) {
            ThreadItem(
                thread = it,
                users = user,
                navHostController = navController,
                userId = ""
            )
        }
    }

}