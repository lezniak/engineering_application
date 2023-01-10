package com.example.apiapp.presentation.afterLogin.events.membersEvent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.common.*
import com.example.apiapp.data.objects.Dao.UserAcceptList
import com.example.apiapp.navigation.BottomNavItem
import com.example.apiapp.presentation.ui.theme.Purple200

@Composable
fun MembersScreen(navHostController: NavHostController,viewModel : MembersViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            title = {
                androidx.compose.material3.Text(
                    "Szczegóły wydarzenia",
                    maxLines = 1,
                    fontSize = 18.sp
                    //overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                    val painter = painterResource(id = R.drawable.ic_baseline_group_add_24)
                    IconButton(onClick = {
                        navHostController.navigate(BottomNavItem.EventAccept.screen_route + "?eventId=${viewModel.eventId}")
                    }) {
                        Icon(painter = painter, contentDescription = "Accept users")
                    }
            },
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack()}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Test")
                }
            }
        )
    }) {
        when (val state = viewModel.state.value) {
            is UIState.Error -> {
                NoElementList()
            }
            is UIState.Loading -> {
                Loading()
            }
            is UIState.Success<*> -> {
                Screen(list = state.result as List<UserAcceptList>)
            }
        }
    }
}
@Composable
private fun Screen(list: List<UserAcceptList>,viewModel: MembersViewModel = hiltViewModel(),) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(list) { index, item ->
            UserCard(item = item)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun UserCard(item: UserAcceptList){
    val dismissState = rememberDismissState(initialValue = DismissValue.Default,confirmStateChange ={
        true
    })


    SwipeToDismiss(
        state = dismissState,
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.EndToStart -> Color.Red
                else -> {
                    Color.Transparent}
            }
            val direction = dismissState.dismissDirection

            if (direction == DismissDirection.EndToStart) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.heightIn(5.dp))
                        Text(
                            text = "Usuń",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )

                    }
                }
            }
        },

        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                colors = CardDefaults.cardColors(contentColor = Purple200, containerColor = Purple200)
            ) {
                Row(
                    Modifier
                        .padding(8.dp)
                ) {
                    Text(text = item.name, color = Color.White)
                }
            }
        },
        directions = setOf(DismissDirection.EndToStart),
    )

}