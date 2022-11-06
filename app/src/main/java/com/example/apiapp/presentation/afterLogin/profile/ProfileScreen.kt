package com.example.apiapp.presentation.afterLogin.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apiapp.R
import com.example.apiapp.data.objects.Setting
import com.example.apiapp.presentation.activity.AfterLoginActivity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(){
    val settings = listOf(
        Setting("Zakupione bilety","Zobacz swoje zakupione bilety"),
        Setting("Ustaw odległość","Ustaw interesującą dla Ciebie ogległość do wydarzeń"))
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            val painter = painterResource(id = R.drawable.missing_avatar)
            Image(
                painter = painter, contentDescription = "1", modifier = Modifier
                    .size(175.dp, 175.dp)
                    .clip(CircleShape)
                    .clickable {
                        Log.d("PhotoClick", "Photoclick")
                    }
            )
            Text(text = AfterLoginActivity.userData.name?:"błąd")
            Text(text = AfterLoginActivity.userData.email?:"błąd")
        }
        Column(Modifier.fillMaxWidth()) {
            settings.forEach {
                settingCard(setting = it)
            }
        }
    }
}

@Composable
fun settingCard(setting: Setting){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp, 8.dp, 2.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))) {
        Column() {
            Text(text = setting.title)
            Text(text = setting.desc,color = Color(128,128,128), fontSize = 12.sp )
        }
    }
}