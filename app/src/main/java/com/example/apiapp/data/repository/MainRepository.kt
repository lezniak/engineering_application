package com.example.apiapp.data.repository

import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import retrofit2.Call

interface MainRepository {

    suspend fun getEvent(newEvent: EventDao) : Call<ServiceReturn<EventDao>>

    suspend fun getEventsByRange(range: Int,LatUser : String,LonUser: String) : ServiceReturn<List<Event>>

    suspend fun putEvent(newEvent : EventDao) : ServiceReturn<EventDao>

    suspend fun getMyEvents() : ServiceReturn<List<Event>>

    suspend fun getMyEventsOLld() : ServiceReturn<List<Event>>
}