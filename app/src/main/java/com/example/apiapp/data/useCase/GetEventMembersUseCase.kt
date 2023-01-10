package com.example.apiapp.data.useCase

import com.example.apiapp.common.UIState
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventMembersUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(eventId:Int) : Flow<UIState> = flow {
        try {
            val result = repository.getEventMembers(eventId)
            if (result.value?.objectList.isNullOrEmpty())
                throw NoSuchElementException()

            emit(UIState.Success(result.value?.objectList))
        }catch (Ex:Exception){
            emit(UIState.Error)
        }
    }
}