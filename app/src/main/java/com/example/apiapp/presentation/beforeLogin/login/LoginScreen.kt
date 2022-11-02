package com.example.apiapp.presentation.beforeLogin.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.navigation.Screen
import com.example.apiapp.common.MyButton
import com.example.apiapp.data.Preferences
import com.example.apiapp.presentation.activity.AfterLoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navHostController: NavHostController) {
    start(navHostController)
}

@Composable
fun start(navHostController: NavHostController,viewModel: LoginViewModel = hiltViewModel()) {
    var pref = Preferences(LocalContext.current)
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
            WelcomeText()
            LoginInputs()
            Button()
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(40.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            EndText(navHostController)
        }

        val resultLogin = viewModel.loginResult.observeAsState().value
        val resultSuccess = viewModel.loginSuccess.observeAsState().value
        if(resultLogin != ""){
            ShowDialog(mess = resultLogin!!,scaffoldState)
        }
        val context = LocalContext.current
        if (resultSuccess != null){
            pref.setEmail(viewModel.loginSuccess.value!!.email!!)
            val intent = Intent(context, AfterLoginActivity::class.java)
            intent.putExtra("user",viewModel.loginSuccess.value)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun EndText(navHostController: NavHostController) {
    val context =  LocalContext.current
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp,
        Alignment.CenterHorizontally)){
        Text(text = "Jeśli nie posiadasz konta?", fontSize = 12.sp)
        Text(text = "Kliknij tutaj!", color = MaterialTheme.colors.secondary, fontSize = 12.sp,modifier = Modifier
            .clickable {
                navHostController.navigate(Screen.Register.route)
            })
    }
}

@Composable
fun Button(viewModel: LoginViewModel = hiltViewModel()){
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        MyButton(onClick = {
            viewModel.loginToService()
        }) {
            Text(text = "Zaloguj!", color = Color.White)
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginInputs(viewModel: LoginViewModel = hiltViewModel()){
    var pref = Preferences(LocalContext.current)

    var email by rememberSaveable { mutableStateOf(pref.getEmail()) }
    viewModel.loginUser.email = email
    val focusManager = LocalFocusManager.current
    Column(
        Modifier
            .padding(0.dp, 32.dp, 0.dp, 0.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState(), reverseScrolling = true), verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it
                            viewModel.loginUser.email = it},
            label = { Text("E-mail") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it
                            viewModel.loginUser.password = it},
            singleLine = true,
            label = { Text("Hasło") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Email") },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                keyboardController?.hide()
            }),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Favorite else Icons.Filled.Send
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                } })

    }

}
@Composable
fun WelcomeText(){
    Text(text = "Zaloguj", fontWeight = FontWeight.Bold, fontSize = 26.sp)
    Text(text = "Aby kontynuowac proszę się zalogować.",color = Color(128,128,128), fontSize = 12.sp )
}

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@Composable
fun ShowDialog(mess: String,scaffoldState: ScaffoldState,viewModel: LoginViewModel = hiltViewModel()){
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = mess,
                actionLabel = "Ok!"
            )
            viewModel.setLoginResult("")
        }
}