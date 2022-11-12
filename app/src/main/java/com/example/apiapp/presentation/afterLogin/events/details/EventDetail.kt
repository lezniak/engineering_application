package com.example.apiapp.presentation.afterLogin.events.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.data.objects.Event
import com.example.apiapp.presentation.activity.AfterLoginActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetail(navHostController: NavHostController,event : Event) {
    Column() {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            title = {
                Text(
                    "Szczegóły wydarzenia",
                    maxLines = 1,
                    fontSize = 18.sp
                    //overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                Modifier
                    .padding(8.dp)) {
                Row() {
                    Text(text = event.name, fontSize = 18.sp)
                    Spacer(Modifier.weight(1f).fillMaxWidth())
                    ImageWithText(event.ownerName)
                }

                Text(text = event.eventDescription)
                Text(text = event.maxMembers.toString())
            }
        }
    }
}

@Composable
fun ImageWithText(text: String){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
        val painter = painterResource(id = R.drawable.missing_avatar)
        Image(
            painter = painter, contentDescription = "1", modifier = Modifier
                .size(25.dp, 25.dp)
                .clip(CircleShape)
                .clickable {
                    Log.d("PhotoClick", "Photoclick")
                },
            alignment = Alignment.Center
        )
        androidx.compose.material.Text(text, fontSize = 14.sp)
    }
}