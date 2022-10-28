package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {
    @GET("event")
    fun getEvent(@Query("eventId") eventId: Int): Call<ServiceReturn<Event>>
}