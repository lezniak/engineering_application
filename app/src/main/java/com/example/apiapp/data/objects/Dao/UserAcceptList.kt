package com.example.apiapp.data.objects.Dao

data class UserAcceptList(
    val accepted: Boolean,
    val hobbyList: List<Any>,
    val name: String,
    val userId: Int
)