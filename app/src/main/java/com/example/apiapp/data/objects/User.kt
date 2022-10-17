package com.example.apiapp.data.objects

import java.time.LocalDate

data class User(
    var birthdate: LocalDate,
    var city: String,
    var email: String,
    var matchingPassword: String,
    var name: String,
    var password: String,
    var phoneNumber: String
)