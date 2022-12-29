package com.example.apiapp.presentation.afterLogin.map

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apiapp.R
import com.example.apiapp.presentation.activity.AfterLoginActivity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun GoogMap(viewModel: MapViewModel = hiltViewModel()){
    val state = viewModel.state.value
    val  cincinati = LatLng(viewModel.lat.toDouble(),viewModel.long.toDouble())
    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    val cameraPosition = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(cincinati,10f)
    }
    viewModel.getEventsByRange()
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState  = cameraPosition,
        uiSettings = uiSettings,
        properties = properties
        )
    {
        if (state.events?.isNotEmpty() == true){
            Log.d("Map_size",state.events?.size.toString())
            state.events?.forEach {
                val info = it.eventAddressInformation
                val eventPosition = LatLng(info.lat.toDouble(),info.lng.toDouble())
                Marker(
                    position = eventPosition,
                    title = it.name,
                    snippet = it.eventDescription
                )
            }
        }
    }

    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        alert(showDialog = showDialog.value,
            onDismiss = {showDialog.value = false})
    }

    val painter = painterResource(id = R.drawable.distance)
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.End) {
        Icon(
            painter,
            contentDescription = null,
            modifier = Modifier.clickable {
                showDialog.value = true
            }
        )
    }
}

@Composable
fun alert(showDialog: Boolean,
          onDismiss: () -> Unit,viewModel: MapViewModel = hiltViewModel()) {
    if (showDialog) {
        AlertDialog(
            title = {
                var sliderPosition by remember { mutableStateOf(viewModel.range.toFloat()) }
                Column(verticalArrangement = Arrangement.Center) {
                    Text(text = "Wybierz zakres wyszukiwania wydarzeń.")

                    Slider(
                        modifier = Modifier.semantics { contentDescription = "Localized Description" },
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it
                                        viewModel.range = it.toInt()},
                    valueRange = 10f..300f)
                    Text(text = sliderPosition.toInt().toString()+" km")
                }
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    viewModel.saveRangeMap()
                    viewModel.getEventsByRange()
                    onDismiss()
                } ) {
                    Text("Akceptuj")

                    //todo funkcja wyszukujaca i odswiezajaca mape
                }
            },
            dismissButton = {}
        )
    }
}