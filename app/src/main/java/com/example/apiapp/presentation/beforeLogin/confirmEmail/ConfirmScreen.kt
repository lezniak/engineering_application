package com.example.apiapp.presentation.beforeLogin.confirmEmail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apiapp.navigation.Screen

@Composable
fun ConfirmScreen(navHostController: NavHostController) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp,Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(12.dp)
        .fillMaxSize()) {
        Text(text = "Gratulacje!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Twoje konto zostało założone prawidłowo. Ostatnim krokiem, aby korzystać z portalu" +
                " należy potwierdzić konto za pomocą linku aktywacyjnego który został wysłany na Twój" +
                " adres email.", textAlign = TextAlign.Center, fontSize = 12.sp)
    }

    Column(verticalArrangement =  Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = { navHostController.navigate(Screen.Login.route) }) {
            Text(text = "Przejdz do logowania")
        }
    }

}