package com.example.apiapp.di

import com.example.apiapp.common.Constants
import com.example.apiapp.data.remote.RegisterApi
import com.example.apiapp.data.repository.implementation.RegisterRepository
import com.example.apiapp.data.repository.implementation.RegisterRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Headers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Headers("Content-Type: application/json")
    fun provideLotrApi(): RegisterApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val request = Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                   // .addHeader("Authorization", "Bearer ${Constants.API_KEY}").build()
                chain.proceed(request.build())
            }.build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
            .create(RegisterApi::class.java)
        return request
    }

    @Provides
    fun provideLotrRepository(api: RegisterApi): RegisterRepository{
        return RegisterRepositoryImpl(api)
    }
}
