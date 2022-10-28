package com.example.apiapp.presentation.afterLogin.HomeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.presentation.activity.AfterLoginActivity

@Composable
fun HomeScreen(navHostController: NavHostController,viewModel: HomeViewModel = hiltViewModel()) {
    Text(text = "Witaj " + AfterLoginActivity.userData.name!!, fontSize = 32.sp)
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
                .padding(8.dp)) {

                    Box(
                        modifier = Modifier.size(50.dp).clip(RectangleShape).background(MaterialTheme.colors.secondary, shape = RoundedCornerShape(10.dp))
                    )
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