package com.example.apiapp.presentation.afterLogin.organistaions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class OrgsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
        savedStateHandle.get<Int>("eventId")?.let {

        }
    }

    fun createOrganisation(organistaionName: String) {

    }
}