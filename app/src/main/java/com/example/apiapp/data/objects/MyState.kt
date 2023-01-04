package com.example.apiapp.data.objects

data class MyState<T>(
    var message: String = "",
    var value: T? = null,
    var status : Int = -1,
    var isLoading : Boolean = false)