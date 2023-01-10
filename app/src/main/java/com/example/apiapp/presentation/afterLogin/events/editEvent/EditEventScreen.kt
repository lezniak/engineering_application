package com.example.apiapp.presentation.afterLogin.events.editEvent

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.common.AppBarWithArrow
import com.example.apiapp.common.MyButton
import com.example.apiapp.navigation.BottomNavItem

@Composable
fun EditEventScreen(navHostController: NavHostController,viewModel: EditEventViewModel = hiltViewModel()) {
    var showPostDialog by rememberSaveable { mutableStateOf(false) }
    Scaffold(topBar = {
        AppBarWithArrow(navHostController = navHostController, title = "Obsługa i edycja wydarzenia")
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyButton(onClick = { navHostController.navigate(BottomNavItem.Organizations.screen_route+ "?eventId=${viewModel.eventId}") }) {
              Text(text = "Organizacje", color = Color.White)
            }
            MyButton(onClick = { navHostController.navigate(BottomNavItem.UsersEvent.screen_route+ "?eventId=${viewModel.eventId}") }) {
                Text(text = "Użytkownicy", color = Color.White)
            }
            MyButton(onClick = { /*TODO*/ }) {
                Text(text = "Edycja wydarzenia", color = Color.White)
            }
            MyButton(onClick = { showPostDialog = true }) {
                Text(text = "Napisz post", color = Color.White)
            }
            MyButton(onClick = { viewModel.getQrCode() }) {
                Text(text = "QR wydarzenia", color = Color.White)
            }
        }
    }

    if (showPostDialog){
        PostDialog(onDismiss = {
            showPostDialog = !showPostDialog
        })
    }
}

@Composable
fun PostDialog(onDismiss : () -> Unit,viewModel: EditEventViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var message by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Teść posta") },
        text = {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Wpisz zawartość posta") }
            )
        },
        buttons = {
            Row(Modifier.padding(16.dp)) {
                MyButton(onClick = { onDismiss() }) {
                    Text(text = "Anuluj")
                }
                Spacer(modifier = Modifier.weight(1f))
                MyButton(onClick = {
                    if (message.length > 5){
                        viewModel.sendPost(message)
                        Toast.makeText(context, "Udało się wysłać post!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    }else{
                        Toast.makeText(context, "Post jest za krótki!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Wyślij")

                } 
            }
            
        }
    )
}