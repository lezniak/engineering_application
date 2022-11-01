package com.example.apiapp.presentation.afterLogin.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun GoogMap(){
    val  cincinati = LatLng(39.74,-84.51)
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
}