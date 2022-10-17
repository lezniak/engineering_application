package com.example.apiapp.data.repository.implementation

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

}