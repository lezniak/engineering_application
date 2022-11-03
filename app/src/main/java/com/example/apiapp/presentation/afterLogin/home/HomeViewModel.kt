package com.example.apiapp.presentation.afterLogin.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.Preferences

import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventsUseCase
import com.example.apiapp.presentation.afterLogin.map.SingleShotLocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
private const val TAG = "Home_View_Model"
@HiltViewModel
class HomeViewModel @Inject constructor(private val getEventsUseCase: GetEventsUseCase, val application: Application) :ViewModel(){
    private var preferences = Preferences(application)
    init {
        getEvents()
    }

    private val _state = mutableStateOf<EventsState>(EventsState())
    val state: State<EventsState> = _state

    private val _loader = mutableStateOf<Boolean>(true)
    val loader: State<Boolean> = _loader

    fun hideLoader(){
        _loader.value = false
    }
     fun getEvents(){
         var range : Int = Preferences(application).getRange()
             SingleShotLocationProvider.requestSingleUpdate(application) { location ->
                 saveLocation(location)
                 viewModelScope.launch(Dispatchers.IO) {
                 getEventsUseCase(range, location.latitude.toString(), location.longitude.toString()).collect {
                     try {
                         _state.value = EventsState(it.value, "correct")
                         Log.e("HOME", "Test")
                     } catch (ex: Exception) {
                         Log.e("HOME", ex.stackTraceToString())
                         _state.value = EventsState(null, "false " + ex.toString())
                     }
                 }
                 }
             }
     }
    private fun saveLocation(location: SingleShotLocationProvider.GPSCoordinates) {
        preferences.setLat(location.latitude.toString())
        preferences.setLong(location.longitude.toString())
    }
}