package com.example.apiapp.data.repository.implementation

import com.example.apiapp.data.objects.RefreshToken
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.User
import dagger.Provides
import retrofit2.Call
import retrofit2.Response


interface RegisterRepository {

    suspend fun test(): Call<String>

    suspend fun registerUser(user: User): Call<ServiceReturn<User>>
}