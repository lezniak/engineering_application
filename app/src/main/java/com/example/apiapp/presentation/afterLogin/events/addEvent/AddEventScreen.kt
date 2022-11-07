package com.example.apiapp.presentation.afterLogin.events.addEvent

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.apiapp.presentation.beforeLogin.register.BackButton

@Composable
fun AddEvent(navHostController: NavHostController) {
    BackButton(navHostController = navHostController,"Dodaj nowe wydarzenie")
}