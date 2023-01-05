package com.example.apiapp.presentation.afterLogin.events.editEvent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        savedStateHandle.get<Int>("eventId")?.let {

        }
    }
}