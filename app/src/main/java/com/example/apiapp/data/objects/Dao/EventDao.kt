package com.example.apiapp.data.objects.Dao

data class EventDao(
    var ownerId: Long =0,
    var name: String ="",
    var eventDescription: String ="",
    var startDate: String ="",
    var eventAddress: EventAddressDto = EventAddressDto(),
    var maxMembers: Int=0,
    var eventType: Long=0,
    var genarateQrCode : Boolean=false,
    var forAll : Boolean = false)



