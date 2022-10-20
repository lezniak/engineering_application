package com.example.apiapp.common

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
        onClick = onClick,
        modifier = modifier,
        content = content
    )
}

fun Map<String,String>.errorList() : Map<String,String>{
    if(this.isNotEmpty()){
        this.forEach {
            Log.e("ERROR_LIST",it.value)
        }
    }

    return this
}