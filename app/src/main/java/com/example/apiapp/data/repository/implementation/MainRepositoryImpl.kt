package com.example.apiapp.data.repository.implementation

import com.example.apiapp.data.objects.Dao.*
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.IdObject
import com.example.apiapp.data.objects.Results.ResultPagin
import com.example.apiapp.data.objects.Results.ServiceReturn
import com.example.apiapp.data.objects.Results.ServiceSimpleReturn
import com.example.apiapp.data.objects.Ticket
import com.example.apiapp.data.remote.MainApi
import com.example.apiapp.data.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
) : MainRepository {
    override suspend fun getEvent(eventId: Int): ServiceReturn<Event> {
        return api.getEvent(eventId)
    }

    override suspend fun getEventsByRange(
        range: Int,
        LatUser: String,
        LonUser: String
    ): ServiceReturn<List<Event>> {
        return api.getEventByRange(range.toString(), LatUser, LonUser)
    }

    override suspend fun putEvent(newEvent: EventDao): ServiceReturn<EventDao> {
        return api.putEvent(newEvent)
    }

    override suspend fun getMyEvents(): ServiceReturn<List<Event>> {
        return api.getMyEvents()
    }

    override suspend fun getMyEventsOLld(): ServiceReturn<List<Event>> {
        return api.getMyEventsOld()
    }

    override suspend fun joinEvent(eventId: IdObject): ServiceSimpleReturn {
        return api.joinEvent(eventId)
    }

    override suspend fun getListUsersToAccept(eventId: Int): ServiceReturn<ResultPagin<UserAcceptList>> {
        return api.getUsersToAccept(eventId)
    }

    override suspend fun sendPost(eventId: Int, content: String) {
        api.sendPost(PostPutDao(eventId,content))
    }

    override suspend fun getPosts(eventId: Int): ServiceReturn<ResultPagin<EventPostInformationDto>> {
        return api.getPosts(eventId)
    }

    override suspend fun acceptUser(eventId: Int, userId: Int) {
        return api.acceptUser(UserToAcceptDao(eventId,userId))
    }

    override suspend fun createOrganiztarion(newOrganizaton: OrganizationCreateDao): Deferred<ServiceReturn<Any>> {
        return CoroutineScope(Dispatchers.IO).async { api.createOrganization(newOrganizaton) }
    }

    override suspend fun deleteOrganizationInEvent(
        eventId: Int,
        organizationId: Int
    ): Deferred<ServiceReturn<Any>> {
        return CoroutineScope(Dispatchers.IO).async { api.deleteOrganizationInEvent(eventId,organizationId) }
    }

    override suspend fun getTasksForMember(user: Int, organizationId: Int): ServiceReturn<ResultPagin<TaskItem>> {
        return api.getMemberTasks(user,organizationId)
    }

    override suspend fun putTask(putTask: TaskPutDao): Deferred<ServiceReturn<Any>> {
        return CoroutineScope(Dispatchers.IO).async { api.putTask(putTask) }
    }

    override suspend fun getEventMembers(eventId: Int): ServiceReturn<ResultPagin<UserAcceptList>> {
        return api.getEventMembers(eventId)
    }

    override suspend fun getQrCodeForEvent(eventId: Int): Any {
        return api.getEventQrCode(eventId)
    }

    override suspend fun putAndGetUserTicket(idObject: IdObject): ServiceReturn<Ticket> {
        return api.putAndGetUserTicket(idObject)
    }

    override suspend fun getAllTicketsForUser(): ServiceReturn<ResultPagin<Any>> {
        return api.getAllUserTickets()
    }

    override suspend fun getOrganizationEvent(eventId: Int): ServiceReturn<ResultPagin<OrganizationItem>> {
        return api.getOrganizationEvent(eventId)
    }


}