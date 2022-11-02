package com.example.apiapp.presentation.afterLogin.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.common.errorList
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
private const val TAG = "Home_View_Model"
@HiltViewModel
class HomeViewModel @Inject constructor(private val mainRepository: MainRepository) :ViewModel(){
    fun getById(){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getEvent(2).enqueue(object : Callback<ServiceReturn<Event>>{
                override fun onResponse(
                    call: Call<ServiceReturn<Event>>,
                    response: Response<ServiceReturn<Event>>
                ) {
                    Log.d(TAG,"Udalo sie")
                    response.body()?.errList?.errorList()
                }

                override fun onFailure(call: Call<ServiceReturn<Event>>, t: Throwable) {
                    Log.d(TAG,"Nie udalo sie")
                }

            })
        }
    }
}