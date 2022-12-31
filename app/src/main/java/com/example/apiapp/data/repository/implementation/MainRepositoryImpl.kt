package com.example.apiapp.data.repository.implementation

import android.util.Log
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.ServiceSimpleReturn
import com.example.apiapp.data.remote.MainApi
import com.example.apiapp.data.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
) : MainRepository {
    override suspend fun getUsersToAccept(eventId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getEvent(eventId: Int): ServiceReturn<Event> {
        return api.getEvent(eventId)
    }

    override suspend fun getEventsByRange(
        range: Int,
        LatUser: String,
        LonUser: String
    ): ServiceReturn<List<Event>> {
        return api.getEventByRange(range.toString(), LatUser, LonUser)
    }

    override suspend fun putEvent(newEvent: EventDao): ServiceReturn<EventDao> {
        return api.putEvent(newEvent)
    }

    override suspend fun getMyEvents(): ServiceReturn<List<Event>> {
        return api.getMyEvents()
    }

    override suspend fun getMyEventsOLld(): ServiceReturn<List<Event>> {
        return api.getMyEventsOld()
    }

    override suspend fun joinEvent(eventId: Long): ServiceSimpleReturn? {
        try {
            Log.e("TAG", "joinEvent:"+eventId.toString() )
            return api.joinEvent(eventId)
        }catch (Ex:Exception){
            Log.e("TAG", "joinEvent: "+ Ex.stackTraceToString())
            return null
        }
    }
}