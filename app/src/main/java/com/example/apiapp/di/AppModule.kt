package com.example.apiapp.di

import com.example.apiapp.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
//
//    @Provides
//    @Singleton
//    fun provideLotrApi(): LotrApi {
//        val request = Retrofit.Builder()
//            .client(OkHttpClient.Builder().addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer ${Constants.API_KEY}").build()
//                chain.proceed(request)
//            }.build())
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(LotrApi::class.java)
//        return request
//    }
}
