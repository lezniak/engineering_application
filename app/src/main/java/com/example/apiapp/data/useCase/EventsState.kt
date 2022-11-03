package com.example.apiapp.data.useCase

import com.example.apiapp.data.objects.Event

data class EventsState(
    var events : List<Event>? = null,
    val msg : String = ""
)
