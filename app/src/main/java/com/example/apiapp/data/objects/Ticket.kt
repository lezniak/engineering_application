package com.example.apiapp.data.objects

data class Ticket(
    val eventId: Int = 0,
    val eventName: String ="",
    val eventStartDate: Long =0L,
    val ownerName: String = "",
    val ticketContent: String = "",
    val ticketId: Int = 0,
    val used: Boolean = false
)