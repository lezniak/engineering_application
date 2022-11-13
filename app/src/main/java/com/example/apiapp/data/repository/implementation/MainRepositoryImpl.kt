package com.example.apiapp.data.repository.implementation

import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.remote.MainApi
import com.example.apiapp.data.remote.RegisterApi
import com.example.apiapp.data.repository.MainRepository
import retrofit2.Call
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
) : MainRepository {
    override suspend fun getEventsByRange(
        range: Int,
        LatUser: String,
        LonUser: String
    ): ServiceReturn<List<Event>> {
        return api.getEventByRange(range.toString(),LatUser.toString(),LonUser.toString())
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
}