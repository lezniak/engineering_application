package com.example.apiapp.common

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@Composable
fun MyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
        onClick = onClick,
        modifier = modifier,
        content = content,
        enabled = enabled
    )
}

fun Map<String,String>.errorList() : Map<String,String>{
    if(this.isNotEmpty()){
        this.forEach {
            Log.e("ERROR_LIST",it.value)
        }
    }

    return this
}

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitCurrentLocation(priority: Int): Location? {
    return suspendCancellableCoroutine {
        // to use for request cancellation upon coroutine cancellation
        val cts = CancellationTokenSource()
        getCurrentLocation(priority, cts.token)
            .addOnSuccessListener {location ->
                // remember location is nullable, this happens sometimes
                // when the request expires before an update is acquired
                it.resume(location) { Log.e("GPS_FETCH_LOCATION", "Error") }
            }

        it.invokeOnCancellation {
            cts.cancel()
        }
    }
}