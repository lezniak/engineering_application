package com.example.apiapp.data.useCase

import android.util.Log
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
private const val TAG = "GET_MY_EVENTS_USE_CASE"

class GetMyEvents @Inject constructor(var repository: MainRepository) {
    operator fun invoke(): Flow<ServiceReturn<List<Event>>> = flow {
        try {
            val eventsList = repository.getMyEvents()
            emit(eventsList)

        }catch (ex: Exception){
            Log.e(TAG,ex.toString())
        }
    }
}