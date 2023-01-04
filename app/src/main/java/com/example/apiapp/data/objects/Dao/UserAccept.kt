package com.example.apiapp.data.objects.Dao

data class UserAccept(
    val accepted: Boolean,
    val hobbyList: List<Any>,
    val name: String,
    val userId: Int
)