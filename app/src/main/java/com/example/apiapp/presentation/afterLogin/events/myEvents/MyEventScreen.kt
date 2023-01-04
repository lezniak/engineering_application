package com.example.apiapp.presentation.afterLogin.events.myEvents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.apiapp.presentation.afterLogin.events.homeEvent.FilterList
import com.example.apiapp.presentation.afterLogin.events.homeEvent.SimpleCircularProgressIndicator
import com.example.apiapp.presentation.afterLogin.events.homeEvent.list

@Composable
fun MyEventScreen(navController: NavHostController,viewModel: MyEventViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    androidx.compose.material.Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            title = {
                androidx.compose.material.Text(
                    "Wydarzenia",
                    maxLines = 1,
                    fontSize = 18.sp
                )
            },
        )
    }) {
        if (state.status){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(4.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                list(list = state.events!!, navController = navController)
            }
        }else if(state.msg == ""){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
                SimpleCircularProgressIndicator()
            }
        }else{
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = state.msg)
            }
        }
    }
}
