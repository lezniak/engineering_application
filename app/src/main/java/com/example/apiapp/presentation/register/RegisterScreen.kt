package com.example.apiapp.presentation.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.common.MyButton
import com.example.apiapp.presentation.home.HomeViewModel
import com.squaredem.composecalendar.ComposeCalendar
import java.time.LocalDate
import java.util.Date

@Composable
fun RegisterScreen(navHostController: NavHostController,viewModel: RegisterViewModel = hiltViewModel()) {
    var stepFlag by rememberSaveable() { mutableStateOf(0) }
    BackButton(navHostController)
    Column(modifier = Modifier
        .padding(40.dp)
        .fillMaxSize(),verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {

        when(stepFlag){
            0 -> StepOneScreen()
            1 -> StepTwoScreen()
            2 -> StepThreeScreen()
            3 -> viewModel.finishRegister()
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            MyButton(onClick = { stepFlag++ }) {
                Text(text = "Dalej", color = Color.White)
            }
        }
    }

}
@Composable
fun StepThreeScreen(viewModel: RegisterViewModel = hiltViewModel()){
    WelcomeText(header = "Ostatni krok!", body = "Aby zakonczyć rejestracje podaj numer telefonu.")
    var number by rememberSaveable { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = number,
            onValueChange = { number = it
                            viewModel.setPhone(it)},
            label = { Text("Numer telefonu") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Call, contentDescription = "Tel Nr") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
    }
}
@Composable
fun StepOneScreen(viewModel: RegisterViewModel = hiltViewModel()){
    WelcomeText(header = "Krok pierwszy", body = "Wprowadź swoje imię, abyśmy wiedzieli jak mamy do Ciebie sie zwracać! No i oczywiście ile masz lat.")
    var name by rememberSaveable { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it
                            viewModel.setName(it)},
            label = { Text("Imie") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.AccountBox, contentDescription = "Email") },
        )
        DatePicker()
    }
}

@Composable
fun DatePicker(viewModel: RegisterViewModel = hiltViewModel()){
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var birthDate by rememberSaveable { mutableStateOf("") }
    if (showDialog) {
        ComposeCalendar(
            onDone = {
                showDialog = false
                birthDate = it.toString()
                viewModel.setBirthDate(it)
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    if (isPressed) {
        showDialog = true
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = birthDate,
        label = { Text("Data urodzenia") },
        readOnly = true,
        onValueChange = { birthDate = it},
        interactionSource = interactionSource,
        leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = "date") }
    )
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
fun PasswordEmailInputs(viewModel: RegisterViewModel = hiltViewModel()) {
    var email by rememberSaveable { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it
                            viewModel.setEmail(it)},
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
            onValueChange = { password = it
                            viewModel.setPassword(it)},
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