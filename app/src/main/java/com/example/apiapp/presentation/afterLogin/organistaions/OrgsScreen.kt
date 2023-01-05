package com.example.apiapp.presentation.afterLogin.organistaions

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.common.AppBarWithArrow
import com.example.apiapp.common.MyButton
import com.example.apiapp.presentation.afterLogin.events.editEvent.EditEventViewModel

@Composable
fun OrgsScreen(navHostController: NavHostController){
    var dialogCreateNew by rememberSaveable { mutableStateOf(false) }
    var dialogAddUserToOrg by rememberSaveable { mutableStateOf(false) }
    Scaffold(topBar = { AppBarWithArrow(navHostController = navHostController, title = "Organizacje")}) {
        Column(Modifier.padding(it)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                MyButton(
                    onClick = { dialogCreateNew = true },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(text = "Stwórz\norganizacje", fontSize = 10.sp, textAlign = TextAlign.Center)
                }
                MyButton(
                    onClick = { dialogAddUserToOrg = true },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(text = "Przypisz\nczłonka", fontSize = 10.sp, textAlign = TextAlign.Center)
                }
            }

        }

        if (dialogCreateNew)
            PostDialog(onDismiss = { dialogCreateNew = !dialogCreateNew })
    }
}

@Composable
fun PostDialog(onDismiss : () -> Unit,viewModel: OrgsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var message by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { androidx.compose.material3.Text("Teść posta") },
        text = {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { androidx.compose.material3.Text("Wpisz zawartość posta") }
            )
        },
        buttons = {
            Row(Modifier.padding(16.dp)) {
                MyButton(onClick = { onDismiss() }) {
                    androidx.compose.material3.Text(text = "Anuluj")
                }
                Spacer(modifier = Modifier.weight(1f))
                MyButton(onClick = {
                    if (message.length > 5){
                        viewModel.createOrganisation(message)
                        Toast.makeText(context, "Udało się wysłać post!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    }else{
                        Toast.makeText(context, "Post jest za krótki!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    androidx.compose.material3.Text(text = "Wyślij")

                }
            }

        }
    )
}
