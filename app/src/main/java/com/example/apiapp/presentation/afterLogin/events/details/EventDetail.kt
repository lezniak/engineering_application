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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.data.objects.Event
import com.example.apiapp.presentation.activity.AfterLoginActivity
import com.example.apiapp.presentation.afterLogin.events.SimpleCircularProgressIndicator

@Composable
fun EventDetail(navHostController: NavHostController,viewModel: EventDetailViewModel = hiltViewModel()) {
    val event = viewModel.state.value

    if (event == null){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleCircularProgressIndicator()
        }
    }else{
        EventDetailWithData(event,navHostController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventDetailWithData(event : Event,navHostController: NavHostController){
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
            actions = {
                if (event.ownerId.toLong() == AfterLoginActivity.userData.id){
                    val painter = painterResource(id = R.drawable.ic_baseline_group_add_24)
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painter,
                            contentDescription = "Accept users"
                        )
                    }
                }else{
                    Text(text = "Dołącz", modifier = Modifier.clickable {
                        Log.d("test","join")
                    })
                }
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
                    Spacer(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth())
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