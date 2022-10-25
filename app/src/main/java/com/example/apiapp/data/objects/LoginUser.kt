package com.example.apiapp.data.objects

data class LoginUser(
    var id: Long,
    var password: String,
    var email : String?,
    var phone: String?,
    var token: String?,
    var refreshToken: String
)
