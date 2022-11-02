package com.example.apiapp.presentation.afterLogin.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.presentation.activity.AfterLoginActivity

@Composable
fun HomeScreen(navHostController: NavHostController,viewModel: HomeViewModel = hiltViewModel()) {
    Column() {
        Text(text = "Witaj " + AfterLoginActivity.userData.name!!, fontSize = 32.sp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
            ) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.height(IntrinsicSize.Max).padding(8.dp)) {
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
}


/*
bottom nawigacja home mapa wydarzenia profil
home - od gory napis centrum sterownia pod nim liczba wydarzen w okolicy najblizsza pod tym lista ostatnich 3 moich utworzonych
wydarzen, pod nimi wydarzenia  proponowane
mapa to zwykla mapa pozwolic wybrac tagi i ile km max 100,
wydarzenia - tworzenie wydarzen
 */