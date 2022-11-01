package com.example.apiapp.presentation.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.apiapp.R
import com.example.apiapp.SetupNavGraphAfterLogin
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.afterLogin.map.SingleShotLocationProvider
import com.example.apiapp.presentation.ui.theme.ApiAppTheme
import com.google.android.gms.location.LocationCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AfterLoginActivity: ComponentActivity() {
    companion object{
        val requestToken = ""
        lateinit var userData: LoginUser
    }
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userData = intent.getParcelableExtra<LoginUser>("user")!!


        setContent {
            ApiAppTheme {
            }
                foo(this)
                MainScreenView()
            }
        }
    }
fun foo(context: Context?) {
    // when you need location
    // if inside activity context = this;
    SingleShotLocationProvider.requestSingleUpdate(context
    ) { location ->
        Log.e(
            "test",
            location?.latitude.toString() + " " + location?.longitude.toString()
        )
    }

}
@Composable
fun MainScreenView(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        SetupNavGraphAfterLogin(navController)
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Map,
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
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
