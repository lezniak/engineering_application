package com.example.apiapp.presentation.afterLogin.events.myEvents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.apiapp.common.AppBarWithArrow
import com.example.apiapp.data.objects.Event
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.afterLogin.events.homeEvent.CardEvent
import com.example.apiapp.presentation.afterLogin.events.homeEvent.SimpleCircularProgressIndicator

@Composable
fun MyEventScreen(navController: NavHostController,viewModel: MyEventViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    Scaffold(topBar = {
        AppBarWithArrow(navController,"Moje Wydarzenia")
    }) {
        if (state.status){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(4.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                List(list = state.events!!, navController = navController)
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

@Composable
private fun List(list : List<Event>, navController: NavController){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(list) { index, item ->
            CardEvent(item) {
                navController.navigate(BottomNavItem.EditEvent.screen_route + "?eventId=${item.id}")
            }
        }
    }
}