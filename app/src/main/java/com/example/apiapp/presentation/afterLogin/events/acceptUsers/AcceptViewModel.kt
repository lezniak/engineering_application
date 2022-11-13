package com.example.apiapp.presentation.afterLogin.events.acceptUsers

import androidx.lifecycle.ViewModel
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AcceptViewModel @Inject constructor(repository: MainRepository) : ViewModel() {
    fun getUsersToAccept(eventId: Int){

    }
}