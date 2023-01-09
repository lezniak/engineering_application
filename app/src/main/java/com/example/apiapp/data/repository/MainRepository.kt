package com.example.apiapp.data.repository

import com.example.apiapp.data.objects.Dao.*
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.IdObject
import com.example.apiapp.data.objects.Results.ResultPagin
import com.example.apiapp.data.objects.Results.ServiceReturn
import com.example.apiapp.data.objects.Results.ServiceSimpleReturn
import kotlinx.coroutines.Deferred

interface MainRepository {
    suspend fun getEvent(eventId: Int) : ServiceReturn<Event>

    suspend fun getEventsByRange(range: Int,LatUser : String,LonUser: String) : ServiceReturn<List<Event>>

    suspend fun putEvent(newEvent : EventDao) : ServiceReturn<EventDao>

    suspend fun getMyEvents() : ServiceReturn<List<Event>>

    suspend fun getMyEventsOLld() : ServiceReturn<List<Event>>

    suspend fun joinEvent(eventId: IdObject) : ServiceSimpleReturn

    suspend fun getListUsersToAccept(eventId: Int) : ServiceReturn<ResultPagin<UserAcceptList>>

    suspend fun sendPost(eventId: Int,content:String)

    suspend fun getPosts(eventId: Int) : ServiceReturn<ResultPagin<EventPostInformationDto>>

    suspend fun acceptUser(eventId: Int,userId: Int)

    suspend fun createOrganiztarion(newOrganizaton: OrganizationCreateDao) : Deferred<ServiceReturn<Any>>

    suspend fun getOrganizationEvent(eventId: Int) : ServiceReturn<ResultPagin<OrganizationItem>>
}