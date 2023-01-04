package com.example.apiapp.presentation.afterLogin.events.addEvent

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apiapp.R
import com.example.apiapp.common.MyButton
import com.example.apiapp.presentation.afterLogin.events.acceptUsers.UIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.squaredem.composecalendar.ComposeCalendar
import java.time.LocalDate

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddEvent(navHostController: NavHostController,viewModel: AddEventViewModel = hiltViewModel()) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val  myPosition = remember{ mutableStateOf(LatLng(viewModel.lati.toDouble(),viewModel.long.toDouble())) }
    val cameraPosition = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(myPosition.value,10f)
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            title = {
                Text(
                    "Stwórz wydarzenie",
                    maxLines = 1,
                    fontSize = 18.sp
                    //overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        )
    }) {
        Column(Modifier.fillMaxSize()) {
            val uiSettings = remember {
                MapUiSettings(myLocationButtonEnabled = false, compassEnabled = false, mapToolbarEnabled = false, zoomControlsEnabled = false)
            }
            val properties by remember {
                mutableStateOf(MapProperties(isMyLocationEnabled = true))
            }

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                cameraPositionState  = cameraPosition,
                uiSettings = uiSettings,
                properties = properties,
                onMapClick = {
                    val locationData = viewModel.getCompleteAddressString(it.latitude,it.longitude,context)
                    viewModel.setCity(locationData[1])
                    viewModel.setStreet(locationData[0])
                    myPosition.value = LatLng(it.latitude,it.longitude)
                    viewModel.eventPosition = LatLng(it.latitude,it.longitude)
                })
            {
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp,Alignment.Top)) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    EventSection(focusManager = focusManager)
                    LocationSection(focusManager = focusManager)
                    AddionalSection(focusManager = focusManager)
                    EndSection(navHostController)
                }
            }
        }
    }
}

@Composable
fun EndSection(navHostController: NavHostController,viewModel: AddEventViewModel = hiltViewModel()) {
    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterHorizontally)) {
        MyButton(onClick = { navHostController.popBackStack()  }) {
            Text(text = "Anuluj",color = Color.White)
        }
       MyButton(onClick = { viewModel.addEvent() }) {
            Text(text = "Utwórz",color = Color.White)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddionalSection(focusManager: FocusManager,viewModel: AddEventViewModel = hiltViewModel()) {
    Text("Opcje dodatkowe")
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(ScrollState(0)), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        var listOfColor = remember {
            mutableStateListOf(false,false,false)
        }

        AssistChip(
            onClick = { listOfColor[0] = !listOfColor[0]
                      viewModel.setGenerateTicket(!listOfColor[0])},
            label = { Text("Generowanie biletów") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[0]) Color(0xFFF9AA33) else Color.White))
        AssistChip(
            onClick = { listOfColor[1] = !listOfColor[1]
                      viewModel.setForall(!listOfColor[1])},
            label = { Text("Dostępne dla wszystkich") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[1]) Color(0xFFF9AA33) else Color.White)
        )
        AssistChip(
            onClick = { listOfColor[2] = !listOfColor[2] },
            label = { Text("Dla dorosłych") },
            colors = AssistChipDefaults.assistChipColors(containerColor = if (listOfColor[2]) Color(0xFFF9AA33) else Color.White)
        )
    }
}
@Composable
private fun EventSection(viewModel: AddEventViewModel = hiltViewModel(), focusManager: FocusManager){
    Text(text = "Informacje o wydarzeniu:")

    TextField(value = viewModel.name.value, onValueChange = { viewModel.setName(it) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
        label = { Text(text = "Nazwa wydarzenia" )},
        modifier = Modifier
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xFFF5F5F5)
        ),
        leadingIcon = { Icon(painterResource(id = R.drawable.ic_baseline_event_24),"Event") })

    TextField(value = viewModel.descripton.value, onValueChange = { viewModel.setDesc(it) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
        label = { Text(text = "Opis wydarzenia" )},
        modifier = Modifier
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xFFF5F5F5)
        ),
        leadingIcon = { Icon(painterResource(id = R.drawable.ic_baseline_description_24),"Desc") })

    DatePicker()
}
@Composable
private fun LocationSection(viewModel: AddEventViewModel = hiltViewModel(),focusManager: FocusManager){
    Text("Lokalizacja wydarzenia:")

    TextField(value = viewModel.city.value, onValueChange = { viewModel.setCity(it) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
        label = { Text(text = "Miasto" )},
        modifier = Modifier
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xFFF5F5F5)),
        leadingIcon = { Icon(painterResource(id = R.drawable.ic_baseline_location_city_24),"City") })

    TextField(value = viewModel.street.value, onValueChange = { viewModel.setStreet(it) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
        label = { Text(text = "Ulica" )},
        modifier = Modifier
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xFFF5F5F5)
        ),
        leadingIcon = { Icon(painterResource(id = R.drawable.road),"Road") })

}

@Composable
private fun DatePicker(viewModel: AddEventViewModel = hiltViewModel()){
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var birthDate by rememberSaveable { mutableStateOf("") }
    if (showDialog) {
        ComposeCalendar(
            onDone = {
                showDialog = false
                birthDate = it.toString()
                viewModel.setStartDate(birthDate)
            },
            maxDate = LocalDate.ofYearDay(2024,1),
            minDate = LocalDate.now(),
            onDismiss = {
                showDialog = false
            }
        )
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    if (isPressed) {
        showDialog = true
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp)),
        value = birthDate,
        label = { Text("Data wydarzenia") },
        readOnly = true,
        onValueChange = { birthDate = it},
        interactionSource = interactionSource,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xFFF5F5F5)),
        leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = "date") }
    )
}