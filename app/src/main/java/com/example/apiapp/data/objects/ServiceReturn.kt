package com.example.apiapp.data.objects

data class ServiceReturn<T>(
    val message : String?,
    val status: Int,
    val errList: Map<String,String>,
    val value : T?,
    val refreshToken: RefreshToken?
)