package com.example.apiapp.presentation.afterLogin.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.apiapp.data.Preferences
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventsUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "Home_View_Model"
@HiltViewModel
class HomeViewModel @Inject constructor(private val getEventsUseCase: GetEventsUseCase, val application: Application,repository: MainRepository) :ViewModel(){
    private var preferences = Preferences(application)
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    private val _state = MutableStateFlow<EventsState>(EventsState())
    val state: StateFlow<EventsState> = _state.asStateFlow()

    @SuppressLint("SuspiciousIndentation")
     fun getEvents(){
         if (ActivityCompat.checkSelfPermission(
                 application,
                 Manifest.permission.ACCESS_FINE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 application,
                 Manifest.permission.ACCESS_COARSE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED
         ) {
            //request permissions
         }

         fusedLocationClient.lastLocation
             .addOnSuccessListener { location : Location? ->
                 getEventsByLocation(location)
             }
             .addOnFailureListener {
                 getEventsByLocation(null)
             }
     }

    private fun getEventsByLocation(location: Location? = null){
        val range : Int = Preferences(application).getRange()
        val lat = location?.latitude?.toString() ?: preferences.getLat()
        val lng = location?.longitude?.toString() ?: preferences.getLong()
        saveLocation(lat,lng)
        CoroutineScope(Dispatchers.IO).launch {
            getEventsUseCase(range, lat, lng).collect {
                try {
                    _state.value = EventsState(it.value, "correct",true)
                    Log.e("HOME", "Test")
                } catch (ex: Exception) {
                    Log.e("HOME", ex.stackTraceToString())
                    _state.value = EventsState(null, "false $ex",false)
                }
            }
        }
    }
    private fun saveLocation(lat: String, lng: String) {
        preferences.setLat(lat)
        preferences.setLong(lng)
    }
}