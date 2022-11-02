package com.example.apiapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
private const val EMAIL_PREF = "EMAIL"

class Preferences(val context: Context) {
    private val preferences = context.getSharedPreferences("Preferences", MODE_PRIVATE)

    fun getEmail():String{
        return preferences.getString(EMAIL_PREF," ")!!
    }

    fun setEmail(arg: String){
        preferences.edit().putString(EMAIL_PREF,arg).apply()
    }
}