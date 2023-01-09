package com.example.apiapp.data.useCase

import android.util.Log
import com.example.apiapp.common.UIState
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG="GetUsersToAcceptUseCase"

class GetUsersToAcceptUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(eventId: Int): Flow<UIState> = flow {
        try {
            val serviceReturn = repository.getListUsersToAccept(eventId)
            //serviceReturn.errList.hasError()
            serviceReturn.value?.objectList?.let { UIState.Success(it) }?.let { emit(it) }
        }catch (ex: Exception){
            Log.e(TAG,ex.stackTraceToString())
            emit(UIState.Error)
        }
    }
}