package com.example.apiapp.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.squaredem.composecalendar.ComposeCalendar
import java.time.LocalDate

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    var stepFlag by rememberSaveable() { mutableStateOf(0) }
    BackButton(navHostController)
    Column(modifier = Modifier
        .padding(40.dp)
        .fillMaxSize(),verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {

        when(stepFlag){
            0 -> StepOneScreen()
            1 -> StepTwoScreen()
            2 -> StepThreeScreen()
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            MyButton(onClick = { stepFlag++ }) {
                Text(text = "Dalej", color = Color.White)
            }
        }
    }

}
@Composable
fun StepThreeScreen(){
    WelcomeText(header = "Ostatni krok!", body = "Aby zakonczyć rejestracje podaj ")
}
@Composable
fun StepOneScreen(){
    WelcomeText(header = "Krok pierwszy", body = "Wprowadź swoje imię, abyśmy wiedzieli jak mamy do Ciebie sie zwracać! No i oczywiście ile masz lat.")
    var name by rememberSaveable { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text("Imie") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.AccountBox, contentDescription = "Email") },
        )

// first declare a variable that holds the dialog visibility state
        val showDialog = rememberSaveable { mutableStateOf(false) }

// then show the dialog based on the state
        if (showDialog.value) {
            ComposeCalendar(
                onDone = { it: LocalDate ->
                    // Hide dialog
                    showDialog.value = false
                    // Do something with the date
                },
                onDismiss = {
                    // Hide dialog
                    showDialog.value = false
                }
            )
        }
    }
}

@Composable
fun StepTwoScreen(){
    WelcomeText("Krok drugi","Aby przejść do następnego kroku wypełnij poprawnie poniższe pola.\"")
    PasswordEmailInputs()
}
@Composable
fun WelcomeText(header : String,body: String){
    Text(text = header, fontWeight = FontWeight.Bold, fontSize = 26.sp)
    Text(text = body,color = Color(128,128,128), fontSize = 12.sp )
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
