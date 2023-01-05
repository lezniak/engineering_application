package com.example.apiapp.presentation.afterLogin.events.editEvent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(savedStateHandle: SavedStateHandle,private val repository: MainRepository) : ViewModel() {
    private var eventId : Int = 0
    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
        }
    }

    fun sendPost(postText:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.sendPost(eventId,postText)
        }
    }
}