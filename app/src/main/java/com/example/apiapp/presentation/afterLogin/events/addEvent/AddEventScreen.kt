package com.example.apiapp.presentation.afterLogin.events.addEvent

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.presentation.beforeLogin.register.BackButton

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddEvent(navHostController: NavHostController,viewModel: AddEventViewModel = hiltViewModel()) {
    BackButton(navHostController = navHostController,"Dodaj nowe wydarzenie")

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        val focusManager = LocalFocusManager.current

        TextField(value = viewModel.eventDao.value, onValueChange = { viewModel.setName(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ))
    }
}