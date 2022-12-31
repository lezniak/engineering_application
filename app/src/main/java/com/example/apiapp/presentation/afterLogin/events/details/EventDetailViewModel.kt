package com.example.apiapp.presentation.afterLogin.events.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.ServiceSimpleReturn
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.repository.implementation.MainRepositoryImpl
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventUseCase
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EventDetailViewModel @Inject constructor(private val getEventUseCase: GetEventUseCase,savedStateHandle: SavedStateHandle,private val repository: MainRepository) : ViewModel() {

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
    //todo sprawdzneie status i wyprintowanie odpowiedniego toasta
    fun sendJoinRequest(eventId: Int){
        viewModelScope.launch {
            invoke(eventId).collect(FlowCollector {
                Log.d("test","test")
            })
        }
    }

    private fun invoke(eventId: Int): Flow<ServiceSimpleReturn> = flow {
        try {
            val eventsList = repository.joinEvent(eventId.toLong())
            emit(eventsList)
        }catch (ex: Exception){
            Log.e("VIEWMODEL_EVENT_DETAIL",ex.toString())
        }
    }
}