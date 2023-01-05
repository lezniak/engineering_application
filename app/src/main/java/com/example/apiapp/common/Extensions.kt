package com.example.apiapp.common

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apiapp.presentation.afterLogin.events.homeEvent.SimpleCircularProgressIndicator
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(title: String,navHostController:NavHostController,backBtn : Boolean = true){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        title = {
            Text(
                title,
                maxLines = 1,
                fontSize = 18.sp
                //overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (backBtn){
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        }
    )
}

fun Map<String,String>.hasError(){
    if (size>0)
        throw CustomException(values.first())
}
@Composable
fun Loading(){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        SimpleCircularProgressIndicator()
    }
}
@Composable
fun NoElementList(){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Sorry! Nic tutaj nie ma.")
    }
}

@Composable
fun AppBarWithArrow(navHostController: NavHostController,title: String){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        title = {
            Text(
                title,
                maxLines = 1,
                fontSize = 18.sp
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
}

