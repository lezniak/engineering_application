package com.example.apiapp.presentation.afterLogin.events.myEvents

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetMyEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyEventViewModel @Inject constructor(private val getMyEventsUseCase: GetMyEventsUseCase): ViewModel() {
    private val _state = mutableStateOf<EventsState>(EventsState())
    val state: State<EventsState> = _state
    init {
        getMyEvents()
    }

    private fun getMyEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            getMyEventsUseCase.invoke().collect(){
                try {
                    _state.value.apply {
                        this.events = it.value
                        this.status = true
                        this.msg = "Pobrano"
                    }
                }catch (Ex:Exception){
                    _state.value.apply {
                        this.status = false
                        this.msg = Ex.stackTraceToString()
                    }
                }
            }
        }
    }
}