package com.example.apiapp.presentation.afterLogin.organistaions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apiapp.data.objects.Dao.OrganizationCreateDao
import com.example.apiapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrgsViewModel @Inject constructor(savedStateHandle: SavedStateHandle,private val repository: MainRepository) : ViewModel() {
    private var eventId = 0
    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
        }
    }

    fun createOrganisation(organiztaionName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createOrganiztarion(OrganizationCreateDao(eventId,organiztaionName))
        }

    }
}