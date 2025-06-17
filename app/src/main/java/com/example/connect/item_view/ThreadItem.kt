package com.example.connect.item_view

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.connect.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.connect.model.ThreadModel
import com.example.connect.model.UserModel
import com.example.connect.utils.SharedPref

//@Preview(showBackground = true)
@Composable
fun ThreadItem(
    thread : ThreadModel,
    users:UserModel,
    navHostController: NavHostController,
    userId: String
) {



        val context= LocalContext.current
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.Top
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
                Spacer(modifier= Modifier.width(10.dp))

                Column(
//                modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        users.userName, style = TextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier= Modifier.height(8.dp))
                    Text(
                        thread.thread, style = TextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier= Modifier.height(8.dp))
//                if(thread.image != "")
                    Image(
                        painter = rememberAsyncImagePainter(model = thread.image),
//                    painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
//            Spacer(modifier= Modifier.height(4.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 0.3.dp, color = Color.DarkGray)
        }


}