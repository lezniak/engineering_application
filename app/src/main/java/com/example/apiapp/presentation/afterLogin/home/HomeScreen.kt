package com.example.apiapp.presentation.afterLogin.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import com.example.apiapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.apiapp.common.CustomAppBar
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.activity.AfterLoginActivity
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navHostController: NavHostController,viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(topBar = { CustomAppBar(title = "Strona główna", navHostController = navHostController,backBtn = false) }) {
        val homeCountState by viewModel.state.collectAsState()
        if (AfterLoginActivity.ifNeedRefresh){
            viewModel.getEvents()
            AfterLoginActivity.ifNeedRefresh = false
        }

        Column {
            InformationLineFirst(countCloseEvents = homeCountState.events?.size?:0)
            InformationLineSecond(navHostController)
            MyEvent()
        }

    }

}
@Composable
private fun MyEvent(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Twoje przyszłe wydarzenie:")

        }

    }
}
@Composable
private fun InformationLineFirst(viewModel: HomeViewModel = hiltViewModel(),countCloseEvents: Int){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
    ) {
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(8.dp)) {
            Text(text = "Wydarzenia w twojej okolicy:", fontSize = 18.sp)
            Text(text = "Ilość wydarzeń która odbędzie sie w najbliższym tygodniu", fontSize = 10.sp)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
            .padding(8.dp),
            horizontalArrangement = Arrangement.End) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RectangleShape)
                    .background(
                        Color(0xFFF9AA33),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(text = countCloseEvents.toString(), color = Color.White, fontSize = 24.sp)
            }
        }

    }
}

@Composable
private fun InformationLineSecond(navHostController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clickable {
                navHostController.navigate(BottomNavItem.AddEvent.screen_route)
            }
            .weight(1f)
            .padding(horizontal = 5.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))){
            Text("Stwórz nowe wydarzenie",modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center)
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .weight(1f)
            .clickable {
                navHostController.navigate(BottomNavItem.Events.screen_route)
            }
            .padding(horizontal = 5.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))){
            Text("Dołącz do istniejącego wydarzenia",modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center)
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .weight(1f)
            .clickable {
                navHostController.navigate(BottomNavItem.MyEvents.screen_route)
            }
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))){
                Text(text = "Edytuj swoje wydarzenia",modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center)
        }
    }
}
@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_lottie))
    LottieAnimation(composition)
}


/*
bottom nawigacja home mapa wydarzenia profil
home - od gory napis centrum sterownia pod nim liczba wydarzen w okolicy najblizsza pod tym lista ostatnich 3 moich utworzonych
wydarzen, pod nimi wydarzenia  proponowane
mapa to zwykla mapa pozwolic wybrac tagi i ile km max 100,
wydarzenia - tworzenie wydarzen
 */