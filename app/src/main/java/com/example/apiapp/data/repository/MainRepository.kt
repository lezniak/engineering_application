package com.example.apiapp.data.repository

import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import retrofit2.Call

interface MainRepository {

    suspend fun getEvent(eventId : Int) : Call<ServiceReturn<Event>>
}