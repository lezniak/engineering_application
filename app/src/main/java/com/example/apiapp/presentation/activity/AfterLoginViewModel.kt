package com.example.apiapp.presentation.activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.Preferences
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AfterLoginViewModel @Inject constructor(private var application: Application, private val repository: MainRepository) : ViewModel() {

}