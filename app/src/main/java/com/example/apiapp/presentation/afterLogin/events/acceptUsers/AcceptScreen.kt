package com.example.apiapp.presentation.afterLogin.events.acceptUsers

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.data.objects.Dao.UserAccept
import com.example.apiapp.presentation.activity.AfterLoginActivity

@Composable
fun AcceptScreen(navHostController: NavHostController,viewModel: AcceptViewModel = hiltViewModel()){
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val stateData = viewModel.state
    Scaffold(scaffoldState = scaffoldState,
    topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            title = {
                Text(
                    "Akceptacja uczestników",
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
    }) {

        when(stateData.value){
            is UIState.Loading -> {

            }
            is UIState.Error ->{

            }
            is UIState.Success ->{
                UserList((stateData.value as UIState.Success).result)
            }
        }
    }
}

@Composable
fun UserList(result: List<UserAccept>) {
    Log.d("test",result.toString())
}
