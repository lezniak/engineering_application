package com.example.apiapp.presentation.afterLogin.map

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
import com.example.apiapp.data.repository.RegisterRepository
import com.example.apiapp.presentation.activity.AfterLoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private var application: Application,private val repository: MainRepository) :  ViewModel()  {
    var range : Int = Preferences(application).getRange()
    private var _events = MutableLiveData<List<Event>>()
    var events: LiveData<List<Event>> = _events
    var lat = Preferences(application).getLat()
    var long = Preferences(application).getLong()


    fun saveRangeMap(){
        AfterLoginActivity.ifNeedRefresh = true
        Preferences(application).setRange(range)
    }

    fun getEventsByRange(){
//        viewModelScope.async(Dispatchers.IO) {
//            val locationUser = AfterLoginActivity.lastLocation
//            repository.getEventsByRange(range,locationUser.latitude.toString(),locationUser.longitude.toString()).enqueue(object : Callback<ServiceReturn<List<Event>>>{
//                override fun onResponse(
//                    call: Call<ServiceReturn<List<Event>>>,
//                    response: Response<ServiceReturn<List<Event>>>
//                ) {
//                    if (response.body()?.value != null)
//                        _events.value = response.body()!!.value
//                }
//
//                override fun onFailure(call: Call<ServiceReturn<List<Event>>>, t: Throwable) {
//                    Log.d("test","Blad")
//                }
//
//            })
//        }
    }
}