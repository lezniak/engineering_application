package com.example.apiapp.data.objects.Dao

data class TaskItem(
    val content: String,
    val memberId: Int,
    val memberName: String,
    val organizationId: Int,
    val organizationName: String,
    val status: String,
    val taskId: Int
)