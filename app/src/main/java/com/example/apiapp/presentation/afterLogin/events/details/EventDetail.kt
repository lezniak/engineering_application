package com.example.apiapp.presentation.afterLogin.events.details

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.EventAddressInformation
import com.example.apiapp.data.objects.IdObject
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.activity.AfterLoginActivity
import com.example.apiapp.presentation.afterLogin.events.SimpleCircularProgressIndicator
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

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
private fun EventDetailWithData(event : Event,navHostController: NavHostController,viewModel: EventDetailViewModel = hiltViewModel()){
    val context = LocalContext.current
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
                    IconButton(onClick = {
                        navHostController.navigate(BottomNavItem.EventAccept.screen_route + "?eventId=${event.id}")
                    }) {
                        Icon(
                            painter = painter,
                            contentDescription = "Accept users"
                        )
                    }
                }else{
                    Text(text = "Dołącz", modifier = Modifier.clickable {
                        viewModel.sendJoinRequest(IdObject(event.id.toLong()))
                        Toast.makeText(context, "Udało się wysłać prośbę o dołączenie", Toast.LENGTH_SHORT).show()
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
        EventDetailsCard(event = event)
    }
}
@Composable
fun ImageWithText(text: String){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsCard(event: Event){
    var isMapShow by remember { mutableStateOf(false) }
    var isAddressShow by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            Modifier
                .padding(8.dp)) {
            Text(text = event.startDate)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(text = event.name, fontSize = 18.sp)
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth())
                ImageWithText(event.ownerName)
            }

            Text(text = event.eventDescription)

            Row() {
                TextButton(onClick = {
                    isMapShow=!isMapShow
                    isAddressShow = false
                }) {
                    Text(text = "Mapa")
                }

                TextButton(onClick = {
                    isAddressShow=!isAddressShow
                    isMapShow = false
                }) {
                    Text(text = "Adres")
                }
            }

        }

        if (isMapShow){
            val uiSettings = remember {
                MapUiSettings(myLocationButtonEnabled = false, compassEnabled = false, zoomControlsEnabled = false)
            }
            val properties by remember {
                mutableStateOf(MapProperties(isMyLocationEnabled = true))
            }
            val pos = remember {
                LatLng(event.eventAddressInformation.lat.toDouble(),event.eventAddressInformation.lng.toDouble())
            }
            val cameraPosition = rememberCameraPositionState{
                position = CameraPosition.fromLatLngZoom(pos,10f)
            }
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                cameraPositionState  = cameraPosition,
                uiSettings = uiSettings,
                properties = properties)
            {

                Marker(position = pos)
            }
        }

        if (isAddressShow){
            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()) {

                Text(text = "Ulica: "+ event.eventAddressInformation.address)
                Text(text = "Miasto: "+ event.eventAddressInformation.city)
            }

        }
    }
}
@Composable
@Preview
fun preview(){
    val address = EventAddressInformation("Krzywa 28","Rybnik",1f,1f)
    val event = Event(address,
    "Testowy opis długi bo chce zobaczyc jak zwija się teskt el zrobie go jeszcze" +
            "dluzszy bo chce troche wiecej zobaczyc no w sumie lorem ipsum moglbym tu wkleic" +
            "ale nie chce mi sie odpalac po raz 13124 przegladarki ",
    0,
    "Testowy obiekt badawczy",
    32,
    0,
    "Daniel",
    "20-20-20 19:30")
    EventDetailsCard(event = event)
}