package com.example.apiapp.data.useCase

import android.util.Log
import com.example.apiapp.data.objects.Dao.EventAddressDto
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
private const val TAG = "CREATE_EVENT_USE_CASE"
class AddEventUseCase @Inject constructor(var repository: MainRepository) {
    operator fun invoke(newEventDao: EventDao): Flow<ServiceReturn<EventDao>> = flow {
        try {
            val eventsList = repository.putEvent(newEventDao)
            emit(eventsList)
        }catch (ex: Exception){
            Log.e(TAG,ex.toString())
        }
    }
}