package com.example.apiapp.presentation.afterLogin.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.apiapp.navigation.BottomNavItem

@Composable
fun HomeScreen(navHostController: NavHostController,viewModel: HomeViewModel = hiltViewModel()) {
    Column {
        InformationLineFirst()
        InformationLineSecond(navHostController)
    }
}

@Composable
private fun InformationLineFirst(){
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
                Text(text = "5", color = Color.White, fontSize = 24.sp)
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
                Log.d("test","test")
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
                //navHostController.navigate(BottomNavItem.Events.screen_route)
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
                Log.d("test","test")
            }
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))){
                Text(text = "Edytuj swoje wydarzenia",modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center)
        }
    }
}


/*
bottom nawigacja home mapa wydarzenia profil
home - od gory napis centrum sterownia pod nim liczba wydarzen w okolicy najblizsza pod tym lista ostatnich 3 moich utworzonych
wydarzen, pod nimi wydarzenia  proponowane
mapa to zwykla mapa pozwolic wybrac tagi i ile km max 100,
wydarzenia - tworzenie wydarzen
 */