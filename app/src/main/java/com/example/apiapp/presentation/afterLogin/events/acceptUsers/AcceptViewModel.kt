package com.example.apiapp.presentation.afterLogin.events.acceptUsers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.useCase.GetUsersToAcceptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AcceptViewModel @Inject constructor(private val getUsersToAcceptUseCase: GetUsersToAcceptUseCase,savedStateHandle: SavedStateHandle,
private val repository: MainRepository) : ViewModel() {
    private val _state = mutableStateOf<UIState>(UIState.Loading)
    val state: State<UIState>
        get() = _state

    init {
        savedStateHandle.get<Int>("eventId")?.let {
            getUsersToAccept(it)
        }
    }

    fun getUsersToAccept(eventId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            getUsersToAcceptUseCase.invoke(eventId).collect(){
                _state.value = it
            }
        }
    }

    fun test() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.acceptUser(11,2)
        }
    }
}

sealed class UIState {
    object Loading : UIState()
    data class Success<T>(val result : T) : UIState()
    object Error : UIState()
}