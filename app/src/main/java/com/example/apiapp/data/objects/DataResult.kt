package com.example.apiapp.data.objects

data class DataResult<T>(
    var status : Int = -1,
    var message : String = "",
    var value : T? = null
)
