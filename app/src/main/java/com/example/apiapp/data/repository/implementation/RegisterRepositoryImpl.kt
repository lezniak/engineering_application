package com.example.apiapp.data.repository.implementation

import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.User
import com.example.apiapp.data.remote.RegisterApi
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterApi
): RegisterRepository{
    override suspend fun test(): Call<String> {
        return api.test()
    }

    override suspend fun registerUser(user: User): Call<ServiceReturn<User>> {
        return api.addUser(user)
    }

}