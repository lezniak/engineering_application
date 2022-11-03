package com.example.apiapp.presentation.afterLogin.events


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apiapp.data.objects.Event

@Composable
fun EventsScreen(viewModel: EventsViewModel = hiltViewModel()){
    val state = viewModel.state.value

    if (state.msg !=""){
        list(list = state.events!!)
    }else{
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleCircularProgressIndicator()
        }
    }
}
@Composable
fun list(list : List<Event>){
    LazyColumn() {
        itemsIndexed(list) { index, item ->
            Text(text = item.eventAddressInformation.city)
        }
    }
}
@Composable
fun SimpleCircularProgressIndicator() {
    CircularProgressIndicator()
}