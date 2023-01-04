package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.Dao.*
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.IdObject
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.ServiceSimpleReturn
import retrofit2.Call
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
    suspend fun getUsersToAccept(@Query("eventId") eventId : Int) : ServiceReturn<ResultPagin<UserAccept>>

    @GET("event-post/posts")
    suspend fun getPosts(@Query("eventId") eventId : Int): ServiceReturn<ArrayList<EventPostInformationDto>>

    @POST("event-post")
    suspend fun sendPost(@Body postPut : PostPutDao)
}