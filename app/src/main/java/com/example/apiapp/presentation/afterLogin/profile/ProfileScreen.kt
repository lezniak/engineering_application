package com.example.apiapp.presentation.afterLogin.profile

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.common.CustomAppBar
import com.example.apiapp.data.objects.Setting
import com.example.apiapp.presentation.activity.AfterLoginActivity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navHostController: NavHostController,viewModel: ProfileViewModel = hiltViewModel()){
    Scaffold(topBar = { CustomAppBar(title = "Ustawienia", navHostController = navHostController,false)}) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
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
                viewModel.settings.forEach {
                    settingCard(setting = it)
                }
            }
        }
    }
}

@Composable
fun settingCard(setting: Setting){
    if (setting.desc.isEmpty() && setting.title.isEmpty()){
        emptyCard()
    }else{
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 2.dp, 8.dp, 2.dp)
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))) {
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = setting.title)
                Text(text = setting.desc,color = Color(128,128,128), fontSize = 12.sp )
            }
        }
    }
}

@Composable
fun emptyCard(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp, 8.dp, 2.dp)) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = "")
            Text(text = "")
        }
    }
}