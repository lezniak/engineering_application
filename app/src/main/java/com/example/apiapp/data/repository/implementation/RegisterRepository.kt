package com.example.apiapp.data.repository.implementation

import dagger.Provides
import retrofit2.Call
import retrofit2.Response


interface RegisterRepository {

    suspend fun test(): Call<String>

   //suspend fun getCharacters(): CharacterDTO
}