package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.Dao.EventAddressDto
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.objects.Event
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import retrofit2.Call
import retrofit2.http.*

interface MainApi {
//    @FormUrlEncoded
//    @POST("event")
//    @Headers("Content-Type: application/json")
//    fun getEvent(@Field("eventAddress") eventAddressDto: EventAddressDto,
//                 @Field("eventDescription") eventDesc: String,
//                 @Field("eventType") type: Long,
//                 @Field("genarateQrCode") qr : Boolean,
//                 @Field("maxMembers") max:Int,
//                 @Field("name") name : String,
//                 @Field("ownerId") id : Long,
//                 @Field("startDate") data : String): Call<ServiceReturn<EventDao>>
    //@FormUrlEncoded
    @POST("event")
    @Headers("Content-Type: application/json")
    fun getEvent(@Body eventDao: EventDao): Call<ServiceReturn<EventDao>>
    @GET("event/range")
    suspend fun getEventByRange(@Query("range") range: String,
                        @Query("userLat") userLat : String,
                        @Query("userLng") userLng: String) : ServiceReturn<List<Event>>

    @GET("event/my-events")
    suspend fun getMyEvents() : ServiceReturn<List<Event>>

    @GET("event/my-events-history")
    suspend fun getMyEventsOld() : ServiceReturn<List<Event>>

    @PUT("event")
    suspend fun putEvent(@Query("createEventDto") eventDao: EventDao) : ServiceReturn<EventDao>
}