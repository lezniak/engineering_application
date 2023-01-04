package com.example.apiapp.data.objects.Dao

data class ResultPagin<T>(
    val last: Boolean,
    val objectList: List<T>,
    val pageNo: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)