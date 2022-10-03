package com.example.apiapp

sealed class Screen(val route: String){
    object Login: Screen("login_screen")
    object Register: Screen("register_screen")
}
