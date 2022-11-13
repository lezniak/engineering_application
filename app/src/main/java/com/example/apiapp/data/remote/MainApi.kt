package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.Dao.EventAddressDto
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import retrofit2.Call
import retrofit2.http.*

interface MainApi {

    @GET("event/range")
    suspend fun getEventByRange(@Query("range") range: String,
                        @Query("userLat") userLat : String,
                        @Query("userLng") userLng: String) : ServiceReturn<List<Event>>

    @GET("event/my-events")
    suspend fun getMyEvents() : ServiceReturn<List<Event>>

    @GET("event/my-events-history")
    suspend fun getMyEventsOld() : ServiceReturn<List<Event>>

    @POST("event")
    suspend fun putEvent(@Body eventDao: EventDao) : ServiceReturn<EventDao>
}