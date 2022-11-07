package com.example.apiapp.data.objects.Dao

data class EventDao(
    var ownerId: Long,
    var name: String,
    var eventDescription: String,
    var startDate: String,
    var eventAddress: EventAddressDto,
    var maxMembers: Int,
    var eventType: Long,
    var genarateQrCode : Boolean,
    var forAll : Boolean)



