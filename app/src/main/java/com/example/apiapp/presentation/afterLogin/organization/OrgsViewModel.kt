package com.example.apiapp.presentation.afterLogin.organization

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.common.UIState
import com.example.apiapp.data.objects.Dao.OrganizationCreateDao
import com.example.apiapp.data.objects.Dao.TaskPutDao
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

    private val _tasksState = mutableStateOf<UIState>(UIState.Loading)
    val tasksState: State<UIState>
        get() = _tasksState

    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
            getOrganizations()
        }
    }

    private fun getOrganizations(){
        _state.value = UIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            getMyOrganizationEvent.invoke(eventId).collect {
                _state.value = it
            }
        }
    }

    fun createOrganisation(organiztaionName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.createOrganiztarion(OrganizationCreateDao(eventId,organiztaionName)).await()

            if (result.status == 1)
                getOrganizations()
        }

    }

    fun deleteOrganization(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.deleteOrganizationInEvent(eventId,id).await()

                if (result.status == 1)
                    getOrganizations()
            }catch (Ex: Exception){
                Log.e("TAG",Ex.stackTraceToString())
            }
        }
    }

    fun getTasksForMember(memberId: Int, idOrganization: Int){
        _tasksState.value = UIState.Loading
        viewModelScope.launch {
            try {
                val resultList = repository.getTasksForMember(memberId,idOrganization)
                if (resultList.value?.objectList.isNullOrEmpty())
                    throw NoSuchElementException()

                _tasksState.value = UIState.Success(resultList.value?.objectList)
            }catch (ex:Exception){
                _tasksState.value = UIState.Error
                Log.d("Test",ex.stackTraceToString())
            }
        }
    }

    fun putTask(memberId: Int,organizationId: Int,content:String){
        viewModelScope.launch {
            val putTast = TaskPutDao(content,memberId, organizationId)
            val result = repository.putTask(putTast).await()

            if (result.status == 1){
                getTasksForMember(memberId,organizationId)
            }
        }
    }
}