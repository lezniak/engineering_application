package com.example.apiapp.data.useCase

import android.util.Log
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.Results.ServiceReturn
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
private const val TAG = "GET_EVENTS_USE_CASE"
class GetEventsUseCase @Inject constructor(private var repository: MainRepository) {
    operator fun invoke(range: Int, latUser: String, lngUser: String): Flow<ServiceReturn<List<Event>>> = flow {
        try {
            val eventsList = repository.getEventsByRange(range,latUser,lngUser)
            if (eventsList != null) {
                emit(eventsList)
            }

        }catch (ex: Exception){
            Log.e(TAG,ex.toString())
        }
    }
}