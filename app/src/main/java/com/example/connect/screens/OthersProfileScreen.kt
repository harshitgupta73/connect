package com.example.connect.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import com.example.connect.utils.SharedPref
import com.example.connect.viewModel.AuthViewModel
import com.example.connect.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OthersProfile(navHostController: NavHostController, uid: String) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val users by userViewModel.users.observeAsState(null)
    val followerList by userViewModel.followersList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    LaunchedEffect(Unit){
        userViewModel.fetchThreads(uid)
        userViewModel.fetchUsers(uid)
        userViewModel.getFollowers(uid)
        userViewModel.getFollowing(uid)
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp )
    ) {
        items(1){
            Column () {
                Spacer(modifier = Modifier.height(30.dp))
                Row (
                    modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Column {
                        Text(
                            users!!.name, style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                            )
                        )
                        Spacer(modifier=Modifier.height(4.dp))
                        Text(
                            users!!.userName, style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier=Modifier.height(16.dp))
                        Text(
                            users!!.bio, style = TextStyle(
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
                        painter = rememberAsyncImagePainter(model = users!!.imageUri),
                        contentDescription = "profile image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier=Modifier.height(8.dp))
                Button(onClick = {
                    userViewModel.followUsers(uid, FirebaseAuth.getInstance().currentUser!!.uid)
                }) {
                    Text(if(followerList!!.contains(FirebaseAuth.getInstance().currentUser!!.uid)) "Following" else "Follow")
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
                users = users!!,
                navHostController = navHostController,
                userId = ""
            )
        }
    }
}