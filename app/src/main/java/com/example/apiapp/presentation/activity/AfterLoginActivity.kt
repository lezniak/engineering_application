package com.example.apiapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.apiapp.SetupNavGraph
import com.example.apiapp.SetupNavGraphAfterLogin
import com.example.apiapp.presentation.ui.theme.ApiAppTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AfterLoginActivity: ComponentActivity() {
    companion object{
        val requestToken = ""
    }
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiAppTheme {
                navController = rememberNavController()
                SetupNavGraphAfterLogin(navHostController = navController)

            }
        }
    }
}