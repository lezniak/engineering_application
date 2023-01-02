package com.example.apiapp.presentation.afterLogin.events.acceptUsers

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.objects.DataResult
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.ServiceSimpleReturn
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AcceptViewModel @Inject constructor(private val repository: MainRepository,savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _state = mutableStateOf<DataResult<Any>>(DataResult<Any>())
    val state: State<DataResult<Any>> = _state

    init {
        savedStateHandle.get<Int>("eventId")?.let {
            getUsersToAccept(it)
        }
    }

    fun getUsersToAccept(eventId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            invokeFetchToAccept(eventId).collect(FlowCollector {
                _state.value.value = it.value
            })
        }
    }
    private fun invokeFetchToAccept(eventId: Int): Flow<ServiceReturn<List<Any>>> = flow {
        try {
            val eventsList = repository.getListUsersToAccept(eventId)
            emit(eventsList)
            _state.value.status = 1
        }catch (ex: Exception){
            if (ex.stackTraceToString().contains("404")){
                _state.value.apply {
                    status = 0
                    message = "Brak użytkowników do akceptacji"
                }
            }else{
                _state.value.apply {
                    status = 0
                    message = ex.toString()
                }
                Log.e("VIEWMODEL_EVENT_DETAIL",ex.toString())
            }
        }
    }
}