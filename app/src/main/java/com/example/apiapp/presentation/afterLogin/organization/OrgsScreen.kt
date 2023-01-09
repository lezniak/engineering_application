package com.example.apiapp.presentation.afterLogin.organization

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.apiapp.common.*
import com.example.apiapp.data.objects.Dao.OrganizationItem
import com.example.apiapp.data.objects.Dao.UserOrganization
import com.example.apiapp.data.objects.Event
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.afterLogin.events.homeEvent.CardEvent

@Composable
fun OrgsScreen(navHostController: NavHostController,viewModel: OrgsViewModel = hiltViewModel()){
    Scaffold(topBar = { AppBarWithArrow(navHostController = navHostController, title = "Organizacje")}) {
        Column(Modifier.padding(it)) {
            ButtonRow()

            when (val state = viewModel.state.value) {
                is UIState.Error -> {
                    NoElementList()
                }
                is UIState.Loading -> {
                    Loading()
                }
                is UIState.Success<*> -> {
                    Screen(list = state.result as List<OrganizationItem>)
                }
            }
        }
    }
}
@Composable
private fun Screen(list: List<OrganizationItem>){
    ListOrganization(list)
}

@Composable
private fun ButtonRow(){
    var dialogCreateNew by rememberSaveable { mutableStateOf(false) }
    var dialogAddUserToOrg by rememberSaveable { mutableStateOf(false) }
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


    if (dialogCreateNew)
        PostDialog(onDismiss = { dialogCreateNew = !dialogCreateNew })
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

@Composable
private fun ListOrganization(list : List<OrganizationItem>){
    list.forEach {
        CardOrganization(item = it)
    }
}

@Composable
private fun ListUsers(list : List<UserOrganization>){
    LazyColumn() {
        itemsIndexed(list) { index, item ->
            CardUserOrganization(item)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardOrganization(item : OrganizationItem) {
    var isShowUsers by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 2.dp, bottom = 2.dp, end = 8.dp)
            .clickable {
                isShowUsers = !isShowUsers
            }
    ) {
        Row(
            Modifier
                .padding(8.dp)
        ) {
            val sizeUsers = item.userOrganizationList?.size?:0

            Text(text = item.name)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = sizeUsers.toString())
        }
        if (isShowUsers)
            item.userOrganizationList?.let { ListUsers(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardUserOrganization(item : UserOrganization, viewModel: OrgsViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                //dialog with tasks
            }
    ) {
        Row(
            Modifier
                .padding(8.dp)
        ) {
            Text(text = item.name)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = item.phoneNumber)
        }
    }
}