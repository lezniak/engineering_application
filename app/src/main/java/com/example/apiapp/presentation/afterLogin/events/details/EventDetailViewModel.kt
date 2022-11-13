package com.example.apiapp.presentation.afterLogin.events.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventUseCase
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EventDetailViewModel @Inject constructor(private val getEventUseCase: GetEventUseCase,savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = mutableStateOf<Event?>(null)
    val state: State<Event?> = _state
    init {
        savedStateHandle.get<Int>("eventId")?.let {
            getEvent(it)
        }
    }
    fun getEvent(eventId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            getEventUseCase.invoke(eventId).collect {
                _state.value = it.value
            }
        }
    }

}