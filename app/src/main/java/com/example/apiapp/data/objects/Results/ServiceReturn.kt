package com.example.apiapp.data.objects.Results

import com.example.apiapp.data.objects.RefreshToken

data class ServiceReturn<T>(
    val message : String?,
    val status: Int,
    val errList: Map<String,String>,
    val value : T?,
    val refreshToken: RefreshToken?
)