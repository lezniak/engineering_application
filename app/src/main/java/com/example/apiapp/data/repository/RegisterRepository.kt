package com.example.apiapp.data.repository

import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.User
import retrofit2.Call
import retrofit2.Response


interface RegisterRepository {

    suspend fun test(): Call<String>

    suspend fun registerUser(user: User): Call<ServiceReturn<User>>

    suspend fun loginUser(user: LoginUser): Call<ServiceReturn<LoginUser>>
}