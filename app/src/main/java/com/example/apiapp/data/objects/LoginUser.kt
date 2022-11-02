package com.example.apiapp.data.objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUser(
    var id: Long,
    var password: String?,
    var email : String?,
    var phoneNumber: String?,
    var locked: String?,
    var name: String?,
    var token: String?,
    var refreshToken: String,
    var lng : Double,
    var lat : Double
) : Parcelable
