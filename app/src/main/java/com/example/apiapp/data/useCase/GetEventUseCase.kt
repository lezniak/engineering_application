package com.example.apiapp.data.useCase

import android.util.Log
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.Results.ServiceReturn
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "GET_EVENT_USE_CASE"
class GetEventUseCase @Inject constructor(var repository: MainRepository) {
    operator fun invoke(eventId : Int): Flow<ServiceReturn<Event>> = flow {
        try {
            val eventsList = repository.getEvent(eventId)
            emit(eventsList)

        }catch (ex: Exception){
            Log.e(TAG,ex.toString())
        }
    }
}