package com.example.apiapp.data.useCase

import android.util.Log
import com.example.apiapp.common.CustomException
import com.example.apiapp.common.UIState
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyOrganizationEvent @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(eventId: Int) = flow {
        try {
            val resultRemote = repository.getOrganizationEvent(eventId).value
            if (resultRemote?.objectList?.isEmpty() == true)
                throw NoSuchElementException()
            emit(UIState.Success(resultRemote?.objectList))
        }catch (ex : Exception){
            emit(UIState.Error)
        }
    }
}