package com.example.apiapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.navigation.Screen
import com.example.apiapp.presentation.afterLogin.home.HomeScreen
import com.example.apiapp.presentation.afterLogin.events.EventsScreen
import com.example.apiapp.presentation.afterLogin.map.GoogMap
import com.example.apiapp.presentation.afterLogin.profile.ProfileScreen
import com.example.apiapp.presentation.beforeLogin.confirmEmail.ConfirmScreen
import com.example.apiapp.presentation.beforeLogin.login.LoginScreen
import com.example.apiapp.presentation.beforeLogin.register.RegisterScreen


@Composable
fun SetupNavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = Screen.Login.route){
        composable(route = Screen.Login.route){
            LoginScreen(navHostController)
        }

        composable(route = Screen.Register.route){
            RegisterScreen(navHostController)
        }

        composable(route = Screen.ConfirmEmail.route){
            ConfirmScreen(navHostController)
        }
    }
}

@Composable
fun SetupNavGraphAfterLogin(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = BottomNavItem.Home.screen_route){
        composable(route = BottomNavItem.Home.screen_route){
            HomeScreen(navHostController)
        }
        composable(BottomNavItem.Map.screen_route){
            GoogMap()
        }
        composable(BottomNavItem.Profile.screen_route){
            ProfileScreen()
        }
        composable(BottomNavItem.Events.screen_route){
            EventsScreen()
        }
    }
}