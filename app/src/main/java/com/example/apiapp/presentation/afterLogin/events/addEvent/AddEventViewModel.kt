package com.example.apiapp.presentation.afterLogin.events.addEvent

import androidx.lifecycle.ViewModel
import com.example.apiapp.data.objects.Dao.EventAddressDto
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.presentation.activity.AfterLoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor() : ViewModel() {
    val eventAddressDto = EventAddressDto("","",0.0,0.0)
    val newEvent = EventDao(AfterLoginActivity.userData.id,"","","",eventAddressDto,0,0,false,false)

}