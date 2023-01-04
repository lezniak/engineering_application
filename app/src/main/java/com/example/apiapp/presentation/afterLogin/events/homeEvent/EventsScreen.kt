package com.example.apiapp.presentation.afterLogin.events.homeEvent


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.data.objects.Event
import com.example.apiapp.navigation.BottomNavItem

@Composable
fun EventsScreen(viewModel: EventsViewModel = hiltViewModel(), navController: NavHostController){
    viewModel.getEventsByRange()
    val state = viewModel.state.value
    var filterShow by remember {mutableStateOf(false)}
    androidx.compose.material.Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            title = {
                Text(
                    "Wydarzenia",
                    maxLines = 1,
                    fontSize = 18.sp
                    //overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { filterShow = !filterShow }) {
                        Icon(painterResource(id = R.drawable.filter), contentDescription = "Localized description")
                    }
            }
        )
    }) {
        if (state.msg !=""){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(4.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                if (filterShow){
                    FilterList()
                }
                list(list = state.events!!, navController = navController)
            }
        }else{
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
                SimpleCircularProgressIndicator()
            }
        }
    }
}
@Composable
fun list(list : List<Event>,navController: NavController){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(list) { index, item ->
            CardEvent(item,navController)
        }
    }
}
@Composable
fun CardEvent(item:Event,navController: NavController){
    Row(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
        .padding(8.dp)
        .clickable {
            navController.navigate(BottomNavItem.Event.screen_route + "?eventId=${item.id}")
        }) {
        Column() {
            Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = item.eventDescription,color = Color(128,128,128), fontSize = 12.sp )
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Text(text = item.startDate.substring(0,10), textAlign = TextAlign.End, fontSize = 12.sp)
            Text(text = item.eventAddressInformation.city, fontSize = 12.sp)

        }
    }    
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterList(){
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(ScrollState(0)), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        var colorOne = remember { mutableStateOf(false) }
        var listOfColor = remember {
            mutableStateListOf(false,false,false,false,false)
        }

        AssistChip(
            onClick = { changeColors(listOfColor,0) },
            label = { Text("Data") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[0]) Color(0xFFF9AA33) else Color.White))
        AssistChip(
            onClick = { changeColors(listOfColor,1) },
            label = { Text("Nazwa") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[1]) Color(0xFFF9AA33) else Color.White)
        )
        AssistChip(
            onClick = { changeColors(listOfColor,2) },
            label = { Text("Dołączone") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[2]) Color(0xFFF9AA33) else Color.White)
        )
        AssistChip(
            onClick = { changeColors(listOfColor,3) },
            label = { Text("W trakcie") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[3]) Color(0xFFF9AA33) else Color.White)
        )
        AssistChip(
            onClick = { changeColors(listOfColor,4) },
            label = { Text("Miejscowość") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[4]) Color(0xFFF9AA33) else Color.White)
        )
    }
}
fun changeColors(listOfColor: SnapshotStateList<Boolean>,select : Int) {
    for (item in 0 until listOfColor.size){
        listOfColor[item] = false
    }
    listOfColor[select] = true
}
@Composable
fun SimpleCircularProgressIndicator() {
    CircularProgressIndicator()
}