package com.example.apiapp.presentation.afterLogin.organization

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.common.UIState
import com.example.apiapp.data.objects.Dao.OrganizationCreateDao
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.useCase.GetMyOrganizationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrgsViewModel @Inject constructor(savedStateHandle: SavedStateHandle,private val repository: MainRepository,private val getMyOrganizationEvent: GetMyOrganizationEvent) : ViewModel() {
    private var eventId = 0

    private val _state = mutableStateOf<UIState>(UIState.Loading)
    val state: State<UIState>
        get() = _state


    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
            getOrganizations(it)
        }
    }

    fun getOrganizations(eventId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            getMyOrganizationEvent.invoke(eventId).collect {
                _state.value = it
            }
        }
    }

    fun createOrganisation(organiztaionName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createOrganiztarion(OrganizationCreateDao(eventId,organiztaionName))
        }

    }
}