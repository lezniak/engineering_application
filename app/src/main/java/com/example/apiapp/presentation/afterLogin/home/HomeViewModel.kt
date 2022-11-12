package com.example.apiapp.presentation.afterLogin.home

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.Preferences
import com.example.apiapp.data.objects.Dao.EventAddressDto
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventsUseCase
import com.example.apiapp.presentation.activity.AfterLoginActivity
import com.example.apiapp.presentation.afterLogin.map.SingleShotLocationProvider
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result.response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


private const val TAG = "Home_View_Model"
@HiltViewModel
class HomeViewModel @Inject constructor(private val getEventsUseCase: GetEventsUseCase, val application: Application,repository: MainRepository) :ViewModel(){
    private var preferences = Preferences(application)
    init {
        getEvents()
        viewModelScope.launch(Dispatchers.IO) {
            val eventAddress = EventAddressDto(
                "tbk",
                "krzy",
                15.000,
                20.000
            )
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val newEvent = EventDao(
                AfterLoginActivity.userData.id,
                "chuj",
                "wuj",
                "2022-11-13 19:30",
                eventAddress,
                2,
                1L,
                true
            )
            Log.w("response", GsonBuilder().setPrettyPrinting().create().toJson(newEvent))
            repository.getEvent(newEvent).enqueue(object : Callback<ServiceReturn<EventDao>>{
                override fun onResponse(
                    call: Call<ServiceReturn<EventDao>>,
                    response: Response<ServiceReturn<EventDao>>
                ) {
                    Log.d("NIE00","tak"+response.isSuccessful+" ??????????? "+AfterLoginActivity.requestToken)
                }

                override fun onFailure(call: Call<ServiceReturn<EventDao>>, t: Throwable) {
                    Log.d("NIE00","nie")
                }

            })
        }

    }

    private val _state = mutableStateOf<EventsState>(EventsState())
    val state: State<EventsState> = _state

     @SuppressLint("SuspiciousIndentation")
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