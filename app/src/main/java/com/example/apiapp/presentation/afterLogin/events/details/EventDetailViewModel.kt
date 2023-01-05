package com.example.apiapp.presentation.afterLogin.events.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.objects.*
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Dao.toPostList
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.repository.implementation.MainRepositoryImpl
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.data.useCase.GetEventUseCase
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
@HiltViewModel
class EventDetailViewModel @Inject constructor(private val getEventUseCase: GetEventUseCase,savedStateHandle: SavedStateHandle,private val repository: MainRepository) : ViewModel() {
    private var eventId = 0
    private val _state = mutableStateOf<Event?>(null)
    val state: State<Event?> = _state

    private val _stateMessage = mutableStateOf<String>("")
    val stateMessgae: State<String> = _stateMessage

    private val _postList = mutableStateOf<List<Post>?>(null)
    val postList: State<List<Post>?> = _postList

    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
            Log.d("VIEWMODEL_EVENT", it.toString())
            getEvent(it)
            getPosts(it)
        }
    }
    fun getEvent(eventId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            getEventUseCase.invoke(eventId).collect {
                _state.value = it.value
            }
        }
    }
    fun clearEvent(){
        _stateMessage.value = ""
    }
    //todo sprawdzneie status i wyprintowanie odpowiedniego toasta
    fun sendJoinRequest(eventId: IdObject){
        viewModelScope.launch {
            invoke(eventId).collect {
                _stateMessage.value = it.message
            }
        }
    }

    private fun invoke(eventId: IdObject): Flow<ServiceSimpleReturn> = flow {
        try {
            val eventsList = repository.joinEvent(eventId)
            emit(eventsList)
        }catch (ex: Exception){
            if (ex.stackTraceToString().contains("409")){
                emit(ServiceSimpleReturn("Już dołączono do wydarzenia."))
            }else{
                Log.e("VIEWMODEL_EVENT_DETAIL",ex.toString())
            }
        }
    }

    fun sendPost(postText:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.sendPost(eventId,postText)
            delay(1000)
            repository.getPosts(eventId)
        }
    }

    private fun getPosts(eventId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postRestulDto = repository.getPosts(eventId)

                if (postRestulDto.status == 1){
                    _postList.value = postRestulDto.value!!.objectList.toPostList()
                }
            }catch (ex:Exception){
                Log.e("TAG", "getPosts: "+ex.stackTraceToString())
            }

        }
    }

    fun convertMilis(milis: Long): String {
        val formatter =  SimpleDateFormat("dd/MM/yyyy");
        val dateString = formatter.format(Date(milis))
        return dateString
    }
}