package com.example.apiapp.data.objects

data class Event(
    val eventAddressInformation: EventAddressInformation,
    val eventDescription: String,
    val id: Int,
    val name: String,
    val ownerId: Int,
    val ownerName: String,
    val startDate: String
)