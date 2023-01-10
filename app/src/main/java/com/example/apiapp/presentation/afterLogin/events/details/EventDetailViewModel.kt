package com.example.apiapp.presentation.afterLogin.events.details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.decode
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.objects.*
import com.example.apiapp.data.objects.Dao.toPostList
import com.example.apiapp.data.objects.Results.ServiceSimpleReturn
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.useCase.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Byte.decode
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

    private val _stateTicket = mutableStateOf<Ticket>(Ticket())
    val stateTicket: State<Ticket> = _stateTicket

    private val _postList = mutableStateOf<List<Post>?>(null)
    val postList: State<List<Post>?> = _postList

    init {
        savedStateHandle.get<Int>("eventId")?.let {
            eventId = it
            Log.d("VIEWMODEL_EVENT", it.toString())
            getEvent(it)
            getPosts(it)
            getTicket()
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

    fun getTicket(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _stateTicket.value = repository.putAndGetUserTicket(IdObject(eventId.toLong())).value!!
            }catch (ex:Exception){
                Log.d("EVENTDETAILVIEWMODEL",ex.stackTraceToString())
            }
        }
    }

    fun getImageTicket(content: String): ImageBitmap {
        val imageBytes = decode(content, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image.asImageBitmap()
    }
}