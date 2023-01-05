package com.example.apiapp.presentation.afterLogin.events.editEvent

import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.common.AppBarWithArrow

@Composable
fun EditEventScreen(navHostController: NavHostController,viewModel: EditEventViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        AppBarWithArrow(navHostController = navHostController, title = "Obsługa i edycja wydarzenia")
    }) {

    }
}