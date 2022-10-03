package com.example.apiapp.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.apiapp.R

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        val painter = painterResource(id = R.drawable.ic_backarr)
        Row() {
            Image(
                modifier = Modifier.clickable { navHostController.popBackStack() }.padding(16.dp),
                painter = painter,
                contentDescription = "",
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.secondary)
            )
        }
        
    }
}