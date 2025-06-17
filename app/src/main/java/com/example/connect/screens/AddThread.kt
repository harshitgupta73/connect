package com.example.connect.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.Layout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.connect.R
import com.example.connect.navigation.Routes
import com.example.connect.utils.SharedPref
import com.example.connect.viewModel.AddThreadViewModel
import com.google.firebase.auth.FirebaseAuth
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun AddThreads(navHostController: NavHostController) {

    var text by remember { mutableStateOf("") }

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val threadViewModel: AddThreadViewModel = viewModel()
    val isPosted by threadViewModel.isPosted.observeAsState(false)

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launcher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(isPosted) {
        if (isPosted) {
            text = ""
            imageUri = null
            Toast.makeText(context, "Thread Added", Toast.LENGTH_SHORT).show()
            navHostController.navigate(Routes.HOME.routes) {
                popUpTo(Routes.ADDTHREAD.routes) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navHostController.navigate(Routes.HOME.routes) {
                    popUpTo(Routes.ADDTHREAD.routes) {
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                " New thread", style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 0.2.dp, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = SharedPref.getImageUrl(context = context)),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                SharedPref.getUserName(context), style = TextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 20.sp
                )
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 62.dp)
        )
        {
            if (text.isEmpty()) {
                Text("Start a Thread...", color = Color.Gray)
            }
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, color = Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (imageUri == null) {
            Icon(painter = painterResource(R.drawable.image),
                tint = Color.Gray,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 60.dp)
                    .size(30.dp)
                    .fillMaxWidth()
                    .clickable() {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context,
                            permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permissionToRequest)
                        }
                    })
        } else {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .height(250.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit
                )
                Icon(
                    Icons.Default.Close, contentDescription = null, modifier = Modifier
                        .align(
                            Alignment.TopEnd
                        )
                        .clickable() { imageUri = null }
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Anyone can reply and quote", style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                )
                TextButton(onClick = {

                    if (imageUri == null) {
                        threadViewModel.saveData(
                            thread = text,
                            userId = FirebaseAuth.getInstance().currentUser!!.uid,
                            ""
                        )
                    } else {
                        threadViewModel.saveImage(
                            thread = text,
                            userId = FirebaseAuth.getInstance().currentUser!!.uid,
                            imageUri = imageUri!!
                        )
                    }
                }) {
                    Text(
                        "Post", style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }


    }
}

