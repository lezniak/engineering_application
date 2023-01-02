package com.example.apiapp.data.repository

import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.IdObject
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.ServiceSimpleReturn
import retrofit2.Call

interface MainRepository {
    suspend fun getEvent(eventId: Int) : ServiceReturn<Event>

    suspend fun getEventsByRange(range: Int,LatUser : String,LonUser: String) : ServiceReturn<List<Event>>

    suspend fun putEvent(newEvent : EventDao) : ServiceReturn<EventDao>

    suspend fun getMyEvents() : ServiceReturn<List<Event>>

    suspend fun getMyEventsOLld() : ServiceReturn<List<Event>>

    suspend fun joinEvent(eventId: IdObject) : ServiceSimpleReturn

    suspend fun getListUsersToAccept(eventId: Int) : ServiceReturn<List<Any>>
}