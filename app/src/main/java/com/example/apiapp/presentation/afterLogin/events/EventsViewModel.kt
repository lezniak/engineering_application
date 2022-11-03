package com.example.apiapp.presentation.afterLogin.events

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.Preferences
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EventsViewModel @Inject constructor(private val getEventsUseCase: GetEventsUseCase, application: Application): ViewModel() {
    private val preferences = Preferences(application)
    private val _state = mutableStateOf<EventsState>(EventsState())
    val state: State<EventsState> = _state
    init {
        getEventsByRange()
    }
    fun getEventsByRange(){
        viewModelScope.launch(Dispatchers.IO) {
            getEventsUseCase(preferences.getRange(), preferences.getLat(), preferences.getLong()).collect {
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