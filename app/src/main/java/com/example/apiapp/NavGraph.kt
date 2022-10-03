package com.example.apiapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apiapp.presentation.login.LoginScreen
import com.example.apiapp.presentation.register.RegisterScreen


@Composable
fun SetupNavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = Screen.Login.route){
        composable(route = Screen.Login.route){
            LoginScreen(navHostController)
        }

        composable(route = Screen.Register.route){
            RegisterScreen(navHostController)
        }
    }
}