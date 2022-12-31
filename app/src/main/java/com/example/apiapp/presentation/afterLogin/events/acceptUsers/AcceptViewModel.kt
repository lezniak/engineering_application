package com.example.apiapp.presentation.afterLogin.events.acceptUsers

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.apiapp.data.objects.ServiceSimpleReturn
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AcceptViewModel @Inject constructor(private val repository: MainRepository,savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        savedStateHandle.get<Int>("eventId")?.let {
            Log.e("TAG", "eventID: "+it.toString() )
        }
    }
    fun getUsersToAccept(eventId: Int){

    }

    private fun invokeFetchToAccept(eventId: Int): Flow<ServiceSimpleReturn> = flow {
        try {
            val eventsList = repository
            emit(eventsList)
        }catch (ex: Exception){
            Log.e("VIEWMODEL_EVENT_DETAIL",ex.toString())
        }
    }
}