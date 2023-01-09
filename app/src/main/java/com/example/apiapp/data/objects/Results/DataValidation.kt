package com.example.apiapp.data.objects.Results

data class DataValidation<T>(
    var data : T,
    var isError: Boolean = false
)
