package com.example.apiapp.presentation.afterLogin.events.acceptUsers

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.common.AppBarWithArrow
import com.example.apiapp.common.Loading
import com.example.apiapp.common.NoElementList
import com.example.apiapp.data.objects.Dao.UserAcceptList

@Composable
@Suppress("UNCHECKED_CAST")
fun AcceptScreen(navHostController: NavHostController,viewModel: AcceptViewModel = hiltViewModel()){
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
    topBar = {
        AppBarWithArrow(navHostController,"Akceptacja użytkowników")
    }) {

        when(val stateData = viewModel.state.value){
            is UIState.Loading -> {
                Loading()
            }
            is UIState.Error ->{

            }
            is UIState.Success<*> ->{
                val data = stateData.result as List<UserAcceptList>
                if (data.isEmpty()){
                    NoElementList()
                }else{
                    UserList(data)
                    androidx.compose.material.Button(onClick = { viewModel.test() }) {
                        Text("PUT")
                    }
                }
            }
        }
    }
}

@Composable
fun UserList(result: List<UserAcceptList>) {
    Text(text = result.toString())
}
