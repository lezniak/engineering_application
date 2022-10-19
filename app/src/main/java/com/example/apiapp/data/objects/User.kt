package com.example.apiapp.data.objects

import java.time.LocalDate

data class User(
    var birthdate: String,
    var city: String,
    var email: String,
    var matchingPassword: String,
    var name: String,
    var password: String,
    var phoneNumber: String
)