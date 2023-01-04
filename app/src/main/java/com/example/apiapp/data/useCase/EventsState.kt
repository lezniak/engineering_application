package com.example.apiapp.data.useCase

import com.example.apiapp.data.objects.Event

data class EventsState(
    var events : List<Event>? = null,
    var msg : String = "",
    var status : Boolean= false
)
