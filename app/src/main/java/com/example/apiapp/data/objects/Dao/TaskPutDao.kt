package com.example.apiapp.data.objects.Dao

data class TaskPutDao(
    val content: String,
    val memberId: Int,
    val organizationId: Int
)