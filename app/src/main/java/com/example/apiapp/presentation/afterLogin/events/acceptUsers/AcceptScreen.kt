package com.example.apiapp.presentation.afterLogin.events.acceptUsers

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.presentation.activity.AfterLoginActivity

@Composable
fun AcceptScreen(navHostController: NavHostController){
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
    topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            title = {
                Text(
                    "Szczegóły wydarzenia",
                    maxLines = 1,
                    fontSize = 18.sp
                    //overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        )
    }) {

    }
}