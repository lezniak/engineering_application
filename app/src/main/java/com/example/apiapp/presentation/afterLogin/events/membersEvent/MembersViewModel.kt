package com.example.apiapp.presentation.afterLogin.events.membersEvent

import android.util.Log
import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.common.UIState
import com.example.apiapp.data.useCase.GetEventMembersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class MembersViewModel @Inject constructor(savedStateHandle: SavedStateHandle,private var getEventMembersUseCase: GetEventMembersUseCase) : ViewModel(){
    var eventId by Delegates.notNull<Int>()

    private val _state = mutableStateOf<UIState>(UIState.Loading)
    val state: State<UIState>
        get() = _state
    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
            getMembersList()
        }
    }

    private fun getMembersList(){
        viewModelScope.launch {
            getEventMembersUseCase.invoke(eventId).collect {
               _state.value = it
            }
        }
    }
}