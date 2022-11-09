package com.example.apiapp.presentation.afterLogin.map

import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.Preferences
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventsUseCase
import com.example.apiapp.presentation.activity.AfterLoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(private var application: Application,private val getEventsUseCase: GetEventsUseCase) :  ViewModel()  {
    var range : Int = Preferences(application).getRange()
    private var preferences = Preferences(application)
    private var _events = MutableLiveData<List<Event>>()
    var events: LiveData<List<Event>> = _events
    var lat = Preferences(application).getLat()
    var long = Preferences(application).getLong()

    private val _state = mutableStateOf<EventsState>(EventsState())
    val state: State<EventsState> = _state

    fun saveRangeMap(){
        AfterLoginActivity.ifNeedRefresh = true
        Preferences(application).setRange(range)
    }

    init {
        getEventsByRange()
    }

    fun getEventsByRange(){
        viewModelScope.launch(Dispatchers.IO) {
            getEventsUseCase(range, preferences.getLat(), preferences.getLong()).collect {
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