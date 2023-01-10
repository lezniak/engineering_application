package com.example.apiapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.navigation.Screen
import com.example.apiapp.presentation.afterLogin.events.homeEvent.EventsScreen
import com.example.apiapp.presentation.afterLogin.events.acceptUsers.AcceptScreen
import com.example.apiapp.presentation.afterLogin.events.addEvent.AddEvent
import com.example.apiapp.presentation.afterLogin.events.details.EventDetail
import com.example.apiapp.presentation.afterLogin.events.editEvent.EditEventScreen
import com.example.apiapp.presentation.afterLogin.events.membersEvent.MembersScreen
import com.example.apiapp.presentation.afterLogin.events.myEvents.MyEventScreen
import com.example.apiapp.presentation.afterLogin.home.HomeScreen
import com.example.apiapp.presentation.afterLogin.map.GoogMap
import com.example.apiapp.presentation.afterLogin.organization.OrgsScreen
import com.example.apiapp.presentation.afterLogin.profile.ProfileScreen
import com.example.apiapp.presentation.beforeLogin.confirmEmail.ConfirmScreen
import com.example.apiapp.presentation.beforeLogin.login.LoginScreen
import com.example.apiapp.presentation.beforeLogin.register.RegisterScreen


@Composable
fun SetupNavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = Screen.Login.route){
        composable(route = Screen.Login.route){
           LoginScreen(navHostController)
//            val address = EventAddressInformation("Krzywa 28","Rybnik",0.0f,0.0f)
//            var event = Event(address,"Grill u Daniela",0,"Grill u mne!",5,2,"Daniel","20.11.2022")
//            EventDetail(navHostController = navHostController,event)
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
fun SetupNavGraphAfterLogin(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = BottomNavItem.Home.screen_route) {
        composable(route = BottomNavItem.Home.screen_route) {
            HomeScreen(navHostController)
        }
        composable(BottomNavItem.Map.screen_route) {
            GoogMap(navHostController)
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen(navHostController = navHostController)
        }
        composable(BottomNavItem.Events.screen_route) {
            EventsScreen(navController = navHostController)
        }
        composable(BottomNavItem.AddEvent.screen_route) {
            AddEvent(navHostController = navHostController)
        }
        composable(BottomNavItem.MyEvents.screen_route) {
            MyEventScreen(navHostController)
        }


        composable(
            BottomNavItem.Event.screen_route + "?eventId={eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    defaultValue = 0
                    type = NavType.IntType
                })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            eventId?.let { event ->
                EventDetail(navHostController = navHostController)
            }
        }

        composable(
            BottomNavItem.Organizations.screen_route + "?eventId={eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    defaultValue = 0
                    type = NavType.IntType
                })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            eventId?.let { event ->
                OrgsScreen(navHostController = navHostController)
            }
        }

        composable(
            BottomNavItem.UsersEvent.screen_route + "?eventId={eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    defaultValue = 0
                    type = NavType.IntType
                })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            eventId?.let { event ->
                MembersScreen(navHostController = navHostController)
            }
        }

        composable(
            BottomNavItem.EditEvent.screen_route + "?eventId={eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    defaultValue = 0
                    type = NavType.IntType
                })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            eventId?.let { event ->
                EditEventScreen(navHostController)
            }
        }

        composable(
            BottomNavItem.EventAccept.screen_route + "?eventId={eventId}", arguments = listOf(
                navArgument("eventId") {
                    defaultValue = 0
                    type = NavType.IntType
                })
        ) {
            val eventId = it.arguments?.getInt("eventId")

            eventId?.let { event ->
                AcceptScreen(navHostController = navHostController)
            }
        }
    }
}
