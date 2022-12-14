package com.example.apiapp.presentation.afterLogin.events.details

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.common.MyButton
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.EventAddressInformation
import com.example.apiapp.data.objects.IdObject
import com.example.apiapp.data.objects.Ticket
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.activity.AfterLoginActivity
import com.example.apiapp.presentation.afterLogin.events.homeEvent.SimpleCircularProgressIndicator
import com.example.apiapp.presentation.afterLogin.organization.OrgsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun EventDetail(navHostController: NavHostController,viewModel: EventDetailViewModel = hiltViewModel()) {
    val event = viewModel.state.value
    val messageState = viewModel.stateMessgae.value
    val context = LocalContext.current
    if (event == null){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleCircularProgressIndicator()
        }
    }else{
        EventDetailWithData(event,navHostController)
    }

    if (messageState.isNotEmpty()){
        Toast.makeText(context, messageState, Toast.LENGTH_SHORT).show()
        viewModel.clearEvent()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventDetailWithData(event : Event,navHostController: NavHostController,viewModel: EventDetailViewModel = hiltViewModel()){
        Scaffold(topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
                title = {
                    Text(
                        "Szczeg????y wydarzenia",
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
                        Text(text = "Do????cz", modifier = Modifier.clickable {
                            viewModel.sendJoinRequest(IdObject(event.id.toLong()))
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
        },
        bottomBar = {
            OptionsForUser()
        }) {
            Column(modifier = Modifier.padding(paddingValues = it)) {
                EventDetailsCard(event = event)
                Divider(color = Color.Black, thickness = 1.dp)
                PostSegment()
            }

    }
}

@Composable
fun OptionsForUser(viewModel: EventDetailViewModel = hiltViewModel()) {
    var isDialogShow by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        MyButton(onClick = {
            if(viewModel.stateTicket.value.ticketContent != "")
                isDialogShow = true
        }) {
            Text(text = "Bilet", color = Color.White)
        }

        MyButton(onClick = { /*TODO*/ }) {
            Text(text = "Zadania", color = Color.White)
        }
    }

    if (isDialogShow)
        TicketDialog({ isDialogShow = false}, item = viewModel.stateTicket.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostSegment(viewModel: EventDetailViewModel = hiltViewModel()){
    val postListState = viewModel.postList.value

    Column(Modifier.verticalScroll(ScrollState(0)),verticalArrangement = Arrangement.spacedBy(0.dp)) {
        if (!postListState.isNullOrEmpty()){
            postListState.forEach {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(text = viewModel.convertMilis(it.createdAt))
                        Text(text = it.content)
                    }
                }
            }
        }
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
private fun TicketDialog(onDismiss : () -> Unit,viewModel: EventDetailViewModel = hiltViewModel(),item: Ticket) {
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp).background(Color.White)) {Text("Bilet na \n"+item.eventName)
                Image(bitmap = viewModel.getImageTicket(item.ticketContent),
                contentDescription = "ticketQr",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp))
            MyButton(onClick = { onDismiss() }) {
                Text(text = "Anuluj")
            }
            }
        })

        }



@Composable
@Preview
fun preview(){
    val address = EventAddressInformation("Krzywa 28","Rybnik",1f,1f)
    val event = Event(address,
    "Testowy opis d??ugi bo chce zobaczyc jak zwija si?? teskt el zrobie go jeszcze" +
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
