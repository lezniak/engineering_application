package com.example.apiapp.presentation.afterLogin.events.addEvent

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.apiapp.data.objects.Dao.EventAddressDto
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.presentation.activity.AfterLoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor() : ViewModel() {
    private val _eventDao = MutableStateFlow(EventDao().name)
    var eventDao = _eventDao.asStateFlow()

    fun setName(arg: String){
        _eventDao.value = arg
    }
}