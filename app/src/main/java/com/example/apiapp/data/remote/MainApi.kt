package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MainApi {
    @GET("event")
    fun getEvent(@Query("eventId") eventId: Int): Call<ServiceReturn<Event>>

    @GET("event/range")
    suspend fun getEventByRange(@Query("range") range: String,
                        @Query("userLat") userLat : String,
                        @Query("userLng") userLng: String) : ServiceReturn<List<Event>>

    @GET("event/my-events")
    suspend fun getMyEvents() : ServiceReturn<List<Event>>

    @GET("event/my-events-history")
    suspend fun getMyEventsOld() : ServiceReturn<List<Event>>

    @PUT("event")
    suspend fun putEvent(@Query("eventDao") eventDao: EventDao) : ServiceReturn<EventDao>
}