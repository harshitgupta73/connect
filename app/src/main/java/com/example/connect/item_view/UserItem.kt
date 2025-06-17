package com.example.connect.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.connect.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.connect.model.ThreadModel
import com.example.connect.model.UserModel
import com.example.connect.navigation.Routes
import com.example.connect.utils.SharedPref
import com.example.connect.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

//@Preview(showBackground = true)
@Composable
fun UserItem(
    users:UserModel,
    navHostController: NavHostController,
) {

    val userViewModel: UserViewModel = viewModel()
    val followerList by userViewModel.followersList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    LaunchedEffect(Unit){
        userViewModel.getFollowers(FirebaseAuth.getInstance().currentUser!!.uid)
        userViewModel.getFollowing(FirebaseAuth.getInstance().currentUser!!.uid)
    }

        val context= LocalContext.current
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp).clickable(){
                val route = Routes.OTHERUSERS.routes.replace("{data}",users.uid)
                navHostController.navigate(route)
            },
            verticalAlignment = Alignment.Top,
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = users.imageUri),
//                painter = painterResource(R.drawable.man),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier= Modifier.width(8.dp))

            Column(
//                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    users.userName, style = TextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier= Modifier.height(8.dp))
                Text(
                    users.name, style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(modifier= Modifier.width(50.dp))

//            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.Bottom,
//                horizontalAlignment = Alignment.End
//            ) {
//                Button(
//                    onClick = {
//                        userViewModel.followUsers(users.uid, FirebaseAuth.getInstance().currentUser!!.uid)
//                    },
//                ) {
//                    Text(if(followingList!!.contains(FirebaseAuth.getInstance().currentUser!!.uid)) "Following" else "Follow")
//                }
//            }
        }


}