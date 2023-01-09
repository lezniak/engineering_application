package com.example.apiapp.presentation.afterLogin.organization

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.common.*
import com.example.apiapp.data.objects.Dao.OrganizationItem
import com.example.apiapp.data.objects.Dao.TaskItem
import com.example.apiapp.data.objects.Dao.UserOrganization
import com.example.apiapp.presentation.afterLogin.events.editEvent.EditEventViewModel
import com.example.apiapp.presentation.ui.theme.Purple200
import com.example.apiapp.presentation.ui.theme.Teal200

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
                Text(text = "Stwórz\norganizacje", fontSize = 10.sp, textAlign = TextAlign.Center, color = Color.White)
            }
            MyButton(
                onClick = { dialogAddUserToOrg = true },
                modifier = Modifier.weight(0.5f)
            ) {
                Text(text = "Przypisz\nczłonka", fontSize = 10.sp, textAlign = TextAlign.Center, color = Color.White)
            }
        }


    if (dialogCreateNew)
        CreateNewOrganizationDialog(onDismiss = { dialogCreateNew = !dialogCreateNew })
}

@Composable
private fun CreateNewOrganizationDialog(onDismiss : () -> Unit,viewModel: OrgsViewModel = hiltViewModel()) {
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
                        Toast.makeText(context, "Udało się utworzyć nową organizacje!", Toast.LENGTH_SHORT).show()
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DialogUserTaskList(user : UserOrganization, onDismiss : () -> Unit,viewModel: OrgsViewModel = hiltViewModel(),idOrganization: Int) {
    var isShowDialog by rememberSaveable { mutableStateOf(false) }

    viewModel.getTasksForMember(user.id,idOrganization)
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Column(Modifier.background(Color.White, RoundedCornerShape(4.dp)).padding(8.dp)) {
                Text("Lista zadań dla "+ user.name+"\n", fontSize = 18.sp)
                Text(text = "Poniżej znajduje się lista zadań pracownika. Aby się z nim skontaktować kliknij numer telefonu.", fontSize = 12.sp)
                Text(text = user.phoneNumber,fontSize= 12.sp, color = Teal200, modifier = Modifier.clickable {
                    Log.d("ZADZWON", "ZADZWON")
                })
                Text(text = "Lista zadań:")
                Column(
                    Modifier
                        .fillMaxWidth()
                        //.border(border = BorderStroke(1.dp, Color.Black))
                        .height(200.dp)
                        .verticalScroll(ScrollState(0))
                ) {
                    when (val state = viewModel.tasksState.value) {
                        is UIState.Error -> {
                            NoElementList()
                        }
                        is UIState.Loading -> {
                            Loading()
                        }
                        is UIState.Success<*> -> {
                            ListTasks(list = state.result as List<TaskItem>)
                        }
                    }
                }
                Row(Modifier.padding(16.dp)) {
                    MyButton(onClick = { onDismiss() }) {
                        Text(text = "Wyjdz", color = Color.White)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    MyButton(onClick = {
                        isShowDialog = true
                    }) {
                        Text(text = "+", color = Color.White)
                    }
                }
            }
        }
    )

    if (isShowDialog)
        AddTask(onDismiss = { isShowDialog = false }, idOrganization = idOrganization, idMember = user.id)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTasks(list:List<TaskItem>){
        list.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 2.dp, bottom = 2.dp, end = 4.dp)
                    .clickable {

                    },
                colors = CardDefaults.cardColors(containerColor = Purple200, contentColor = Purple200)
            ) {
                Column(Modifier.padding(5.dp)) {
                    Text(text = item.content, color = Teal200)
                    Row() {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = item.status, color = Teal200)
                    }
                }
            }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListOrganization(list : List<OrganizationItem>){
    list.forEach {
        CardOrganization(item = it)
    }
}

@Composable
private fun ListUsers(list: List<UserOrganization>, id: Int){
    LazyColumn() {
        itemsIndexed(list) { index, item ->
            CardUserOrganization(item,id)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun CardOrganization(item : OrganizationItem,viewModel: OrgsViewModel = hiltViewModel()) {
    var isShowUsers by rememberSaveable { mutableStateOf(false) }
    val dismissState = rememberDismissState(initialValue = DismissValue.Default,confirmStateChange ={
        viewModel.deleteOrganization(item.id)
        true
    })

    SwipeToDismiss(
        state = dismissState,
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.EndToStart -> Color.Red
                else -> {Color.Transparent}
            }
            val direction = dismissState.dismissDirection

            if (direction == DismissDirection.EndToStart) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.heightIn(5.dp))
                        Text(
                            text = "Usuń",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )

                    }
                }
            }
        },

        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 2.dp, bottom = 2.dp, end = 8.dp)
                    .clickable {
                        isShowUsers = !isShowUsers
                    },
                colors = CardDefaults.cardColors(containerColor = Teal200, contentColor = Teal200)
            ) {
                Row(
                    Modifier
                        .padding(8.dp)
                ) {
                    val sizeUsers = item.userOrganizationList?.size?:0

                    Text(text = item.name, color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = sizeUsers.toString(), color = Color.White)
                }
                if (isShowUsers)
                    item.userOrganizationList?.let { ListUsers(it,item.id) }
            }
        },
        directions = setOf(DismissDirection.EndToStart),
    )
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardUserOrganization(item : UserOrganization, id: Int ,viewModel: OrgsViewModel = hiltViewModel()) {
    var isDialogShow by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                isDialogShow = !isDialogShow
            },
        colors = CardDefaults.cardColors(contentColor = Purple200, containerColor = Purple200)
    ) {
        Row(
            Modifier
                .padding(8.dp)
        ) {
            Text(text = item.name, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = item.phoneNumber, color = Color.White)
        }
    }
    if (isDialogShow)
        DialogUserTaskList(onDismiss = { isDialogShow = false }, user = item, idOrganization = id)
}

@Composable
fun AddTask(onDismiss : () -> Unit,viewModel: OrgsViewModel = hiltViewModel(),idOrganization: Int,idMember:Int) {
    val context = LocalContext.current
    var message by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Treść zadania", color = Color.Black) },
        text = {
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Wpisz treść zadania", color = Color.Black) }
            )
        },
        buttons = {
            Row(Modifier.padding(16.dp)) {
                MyButton(onClick = { onDismiss() }) {
                    Text(text = "Anuluj", color = Color.White)
                }
                Spacer(modifier = Modifier.weight(1f))
                MyButton(onClick = {
                    if (message.length > 5){
                        viewModel.putTask(idMember,idOrganization,message)
                        Toast.makeText(context, "Udało się utworzyć zadanie!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    }else{
                        Toast.makeText(context, "Treść zadania za krótka!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Wyślij", color = Color.White)

                }
            }

        }
    )
}