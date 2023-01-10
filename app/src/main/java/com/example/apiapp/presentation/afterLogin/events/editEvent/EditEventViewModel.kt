package com.example.apiapp.presentation.afterLogin.events.editEvent

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EDIT_EVENT_VIEW_MODEL"
@HiltViewModel
class EditEventViewModel @Inject constructor(savedStateHandle: SavedStateHandle,private val repository: MainRepository) : ViewModel() {
    var eventId : Int = 0
    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
        }
    }

    fun sendPost(postText:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.sendPost(eventId,postText)
            }catch (ex:Exception){
                Log.d(TAG,ex.stackTraceToString())
            }
        }
    }

    fun getQrCode(){
        viewModelScope.launch {
            try {
                repository.getQrCodeForEvent(eventId)
            }catch (ex:Exception){
                Log.d("TAG",ex.stackTraceToString())
            }
        }
    }
}