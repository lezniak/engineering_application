package com.example.apiapp.di

import com.example.apiapp.common.Constants
import com.example.apiapp.data.remote.MainApi
import com.example.apiapp.data.remote.RegisterApi
import com.example.apiapp.data.repository.MainRepository
import com.example.apiapp.data.repository.RegisterRepository
import com.example.apiapp.data.repository.implementation.MainRepositoryImpl
import com.example.apiapp.data.repository.implementation.RegisterRepositoryImpl
import com.example.apiapp.presentation.activity.AfterLoginActivity
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Headers
import javax.inject.Singleton
import kotlin.math.log


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
    @Singleton
    @Headers("Content-Type: application/json")
    fun mainApi(): MainApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val request = Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(logging).addInterceptor { chain ->
                val request = chain.request().newBuilder()
                 .addHeader("Authorization", "Bearer ${AfterLoginActivity.userData.token}").build()
                chain.proceed(request)
            }.build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
            .create(MainApi::class.java)
        return request
    }

    @Provides
    fun provideLotrRepository(api: RegisterApi): RegisterRepository {
        return RegisterRepositoryImpl(api)
    }

    @Provides
    fun provideMainRepository(api: MainApi): MainRepository {
        return MainRepositoryImpl(api)
    }
}
