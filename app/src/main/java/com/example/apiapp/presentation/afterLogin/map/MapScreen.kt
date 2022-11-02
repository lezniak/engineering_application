package com.example.apiapp.presentation.afterLogin.map

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun GoogMap(){
    val location = AfterLoginActivity.lastLocation
    val  cincinati = LatLng(location.latitude.toDouble(),location.longitude.toDouble())
    val cameraPosition = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(cincinati,10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState  = cameraPosition)
    {
        Marker(
            position = cincinati,
            title = "Test",
            snippet = "Testowy marker")

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
                var sliderPosition by remember { mutableStateOf(0f) }
                Column(verticalArrangement = Arrangement.Center) {
                    Text(text = "Wybierz zakres wyszukiwania wydarzeń.")

                    Slider(
                        modifier = Modifier.semantics { contentDescription = "Localized Description" },
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it
                                        viewModel.range = it},
                    valueRange = 10f..300f)
                    Text(text = sliderPosition.toInt().toString()+" km")
                }
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss ) {
                    Text("Akceptuj")
                    //todo funkcja wyszukujaca i odswiezajaca mape
                }
            },
            dismissButton = {}
        )
    }
}