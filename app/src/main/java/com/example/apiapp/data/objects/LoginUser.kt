package com.example.apiapp.data.objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUser(
    var id: Long,
    var password: String?,
    var email : String?,
    var phone: String?,
    var token: String?,
    var refreshToken: String
) : Parcelable
