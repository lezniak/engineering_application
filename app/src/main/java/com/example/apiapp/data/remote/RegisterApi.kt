package com.example.apiapp.data.remote

import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterApi {
    @Headers("Accept: application/json")
    @POST("user")
    fun addUser(@Body user: User): Call<ServiceReturn<User>>

    @POST("user/login")
    fun loginUser(@Body user: LoginUser): Call<ServiceReturn<LoginUser>>

    @GET("event-api")
    fun test() : Call<String>
}