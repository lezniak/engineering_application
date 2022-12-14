package com.example.apiapp.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.apiapp.R
import com.example.apiapp.SetupNavGraphAfterLogin
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.ui.theme.ApiAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AfterLoginActivity: ComponentActivity() {
    companion object{
        var ifNeedRefresh = true
        val requestToken = ""
        lateinit var userData: LoginUser
//        lateinit var lastLocation: GPSCoordinates
    }
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userData = intent.getParcelableExtra<LoginUser>("user")!!

        setContent {
            ApiAppTheme {
                MainScreenView()
            }
        }
    }
}


@Composable
fun MainScreenView(){
    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "home" -> true // on this screen bottom bar should be hidden
        "map" -> true
        "events" -> true
        "profile" -> true// here too
        else -> false // in all other cases show bottom bar
    }

    Scaffold(
        bottomBar = { if (showBottomBar) BottomNavigation(navController = navController) }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SetupNavGraphAfterLogin(navController)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Map,
        BottomNavItem.Events,
        BottomNavItem.Profile
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.prim700),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title, tint = Color.White) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
