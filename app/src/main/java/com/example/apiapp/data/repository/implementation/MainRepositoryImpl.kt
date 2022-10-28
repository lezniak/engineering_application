package com.example.apiapp.data.repository.implementation

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
    override suspend fun getEvent(eventId: Int): Call<ServiceReturn<Event>> {
        return api.getEvent(eventId)
    }
}