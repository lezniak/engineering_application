package com.example.apiapp.data.objects

data class DataValidation<T>(
    var data : T,
    var isError: Boolean = false
)
