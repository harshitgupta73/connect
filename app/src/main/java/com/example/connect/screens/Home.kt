package com.example.connect.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.example.connect.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.connect.item_view.ThreadItem
import com.example.connect.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

//@Preview(showBackground = true)
@Composable
fun Home(navHostController: NavHostController) {

    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel()
    val threadsAndUsers by homeViewModel.threadsAndUsers.observeAsState(emptyList())



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {



        LazyColumn() {
            
            items(1){
                Image(
                    painter = painterResource(R.drawable.connect),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentScale = ContentScale.Fit
                )
            }
            items(threadsAndUsers ?: emptyList()) { pairs ->
                ThreadItem(
                    thread = pairs.first,
                    users = pairs.second,
                    navHostController = navHostController,
                    userId = FirebaseAuth.getInstance().currentUser!!.uid
                )
            }
        }
    }
}