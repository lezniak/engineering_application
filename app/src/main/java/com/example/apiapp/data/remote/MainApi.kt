package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.Dao.*
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.IdObject
import com.example.apiapp.data.objects.Results.ResultPagin
import com.example.apiapp.data.objects.Results.ServiceReturn
import com.example.apiapp.data.objects.Results.ServiceSimpleReturn
import retrofit2.http.*

interface MainApi {

    @GET("event/range")
    suspend fun getEventByRange(@Query("range") range: String,
                        @Query("userLat") userLat : String,
                        @Query("userLng") userLng: String) : ServiceReturn<List<Event>>

    @GET("event/my-events")
    suspend fun getMyEvents() : ServiceReturn<List<Event>>

    @GET("event/my-events-history")
    suspend fun getMyEventsOld() : ServiceReturn<List<Event>>

    @POST("event")
    suspend fun putEvent(@Body eventDao: EventDao) : ServiceReturn<EventDao>

    @GET("event")
    suspend fun getEvent(@Query("eventId") eventId : Int) : ServiceReturn<Event>

    @POST("event-member/join")
    suspend fun joinEvent(@Body eventId: IdObject) : ServiceSimpleReturn

    @GET("event-member/members/to-accept")
    suspend fun getUsersToAccept(@Query("eventId") eventId : Int) : ServiceReturn<ResultPagin<UserAcceptList>>

    @GET("event-post/posts")
    suspend fun getPosts(@Query("eventId") eventId : Int): ServiceReturn<ResultPagin<EventPostInformationDto>>

    @POST("event-post")
    suspend fun sendPost(@Body postPut : PostPutDao)

    @POST("organization")
    suspend fun createOrganization(@Body newOrganizationCreateDao: OrganizationCreateDao) : ServiceReturn<Any>

    @PUT("event-member/accept")
    suspend fun acceptUser(@Body userToAccept : UserToAcceptDao)

    @GET("organization/all-organizations")
    suspend fun getOrganizationEvent(@Query("eventId") eventId: Int): ServiceReturn<ResultPagin<OrganizationItem>>

    @DELETE("organization")
    suspend fun deleteOrganizationInEvent(@Query("eventId") eventId: Int,@Query("organizationId") organizationId : Int) : ServiceReturn<Any>

    @GET("task/user-organization")
    suspend fun getMemberTasks(@Query("user") userId: Int,@Query("organization") organizationId : Int) : ServiceReturn<ResultPagin<TaskItem>>

    @POST("task")
    suspend fun putTask(@Body task : TaskPutDao) : ServiceReturn<Any>
}