package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterApi {
    @POST("users")
    fun addUser(@Body user: User): Call<User>

    @GET("event-api")
    fun test() : Call<String>
}