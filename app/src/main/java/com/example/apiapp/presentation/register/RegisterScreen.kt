package com.example.apiapp.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.common.MyButton

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    var stepFlag by rememberSaveable() { mutableStateOf(false) }
    BackButton(navHostController)
    Column(modifier = Modifier
        .padding(40.dp)
        .fillMaxSize(),verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        if (!stepFlag){
            StepOneScreen()
        }else{
            StepTwoScreen()
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            MyButton(onClick = { stepFlag = !stepFlag}) {
                Text(text = "Dalej", color = Color.White)
            }
        }
    }

}
@Composable
fun StepTwoScreen(){
    Text(text = "Dalej")
}
@Composable
fun StepOneScreen(){
    WelcomeText()
    PasswordEmailInputs()
}
@Composable
fun WelcomeText(){
    Text(text = "Krok pierwszy", fontWeight = FontWeight.Bold, fontSize = 26.sp)
    Text(text = "Aby przejść do następnego kroku wypełnij poprawnie poniższe pola.",color = Color(128,128,128), fontSize = 12.sp )
}
@Composable
fun PasswordEmailInputs() {
    var email by rememberSaveable { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            label = { Text("Hasło") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Email") },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
fun BackButton(navHostController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        val painter = painterResource(id = R.drawable.ic_backarr)
        Row() {
            Image(
                modifier = Modifier
                    .clickable { navHostController.popBackStack() }
                    .padding(16.dp),
                painter = painter,
                contentDescription = "",
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.secondary)
            )
        }
    }
}
