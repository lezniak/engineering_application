package com.example.apiapp.presentation.beforeLogin.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.navigation.Screen
import com.example.apiapp.common.MyButton
import com.squaredem.composecalendar.ComposeCalendar
import java.time.LocalDate

@Composable
fun RegisterScreen(navHostController: NavHostController,viewModel: RegisterViewModel = hiltViewModel()) {
    var stepFlag by rememberSaveable() { mutableStateOf(0) }
    BackButton(navHostController,"Stw??rz w??asny profil")

    Column(modifier = Modifier
        .padding(40.dp)
        .fillMaxSize(),verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {

        when(stepFlag){
            0 -> StepOneV2()
            1 -> StepTwoV2()
            2 -> viewModel.finishRegister()
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            MyButton(onClick = { stepFlag++ }, enabled = true) {
                Text(text = "Dalej", color = Color.White)
            }


        }
        val list  = viewModel.result.observeAsState().value

        if (list == 1){
            navHostController.navigate(Screen.ConfirmEmail.route)
        }
    }

}
@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun StepOneV2(viewModel: RegisterViewModel = hiltViewModel()){
    val painter = painterResource(id = R.drawable.missing_avatar)
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var name by rememberSaveable() { mutableStateOf(viewModel.nameDV.data) }
    var email by rememberSaveable() { mutableStateOf(viewModel.emailDV.data) }
    var password by rememberSaveable() { mutableStateOf(viewModel.passwordDV.data) }

    viewModel.nameDV.isError = viewModel.validateName(name)
    viewModel.emailDV.isError = viewModel.validationEmail(email)
    viewModel.passwordDV.isError = viewModel.validationPassword(password)

    Column(verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically), modifier = Modifier.height(80.dp)) {
        Row(
            Modifier
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Image(
                painter = painter, contentDescription = "1", modifier = Modifier
                    .size(75.dp, 75.dp)
                    .clip(CircleShape)
                    .clickable {
                        Log.d("PhotoClick", "Photoclick")
                    }
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterVertically), modifier = Modifier
                .padding(0.dp)
                .fillMaxHeight()) {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = email, color = Color.Gray, fontSize = 12.sp)
            }

        }
    }
    val nameClick = remember {
        MutableInteractionSource()
    }
    val emailClick = remember {
        MutableInteractionSource()
    }
    val passwordClick = remember {
        MutableInteractionSource()
    }
    if ( nameClick.collectIsPressedAsState().value)
        name = ""

    if ( emailClick.collectIsPressedAsState().value)
        email = ""

    if ( passwordClick.collectIsPressedAsState().value)
        password = ""

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(value = name,
            onValueChange = {name = it
                            viewModel.setName(it)},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFF5F5F5)),
            interactionSource = nameClick,
            isError = viewModel.nameDV.isError,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
                .clickable {
                    name = ""
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        OutlinedTextField(value = email,
            onValueChange = {email = it
                            viewModel.setEmail(it)},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFF5F5F5)),
            interactionSource = emailClick,
            isError = viewModel.emailDV.isError,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
                .clickable {
                    email= ""
                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ))

        OutlinedTextField(value = password,
            onValueChange = {password = it
                viewModel.setPassword(it)},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFF5F5F5)),
            visualTransformation = PasswordVisualTransformation(),
            interactionSource = passwordClick,
            isError = viewModel.passwordDV.isError,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
                .clickable { password = "" },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                keyboardController?.hide()
            }))
    }
}
@Composable
fun StepTwoV2(){
    DatePicker()
}
@Composable
fun StepThreeScreen(viewModel: RegisterViewModel = hiltViewModel()){
    WelcomeText(header = "Ostatni krok!", body = "Aby zakonczy?? rejestracje podaj numer telefonu.")
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
private fun DatePicker(viewModel: RegisterViewModel = hiltViewModel()){
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var birthDate by rememberSaveable { mutableStateOf("") }
    if (showDialog) {
        ComposeCalendar(
            onDone = {
                showDialog = false
                birthDate = it.toString()
                viewModel.setBirthDate(it)
            },
            maxDate = LocalDate.now(),
            minDate = LocalDate.ofYearDay(1950,1),
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
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp)),
        value = birthDate,
        label = { Text("Data urodzenia") },
        readOnly = true,
        onValueChange = { birthDate = it},
        interactionSource = interactionSource,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xFFF5F5F5)),
        leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = "date")}
    )
}
@Composable
fun StepTwoScreen(){
    WelcomeText("Krok drugi","Aby przej???? do nast??pnego kroku wype??nij poprawnie poni??sze pola.\"")
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
            label = { Text("Has??o") },
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
fun BackButton(navHostController: NavHostController, text : String) {
    val painter = painterResource(id = R.drawable.ic_backarr)
    Row(Modifier.fillMaxWidth()) {
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


    Row() {
            Column(modifier = Modifier.fillMaxWidth(),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = text, modifier = Modifier.padding(16.dp))
        }
    }
}