package com.example.apiapp.data.objects

import java.time.Instant

data class RefreshToken(
    val id : Long,
    val token : String,
    val expirationDate: Instant,
    val user : User
)
