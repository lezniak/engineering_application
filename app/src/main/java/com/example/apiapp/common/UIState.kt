package com.example.apiapp.common

sealed class UIState {
    object Loading : UIState()
    data class Success<T>(val result : T) : UIState()
    object Error : UIState()
}
