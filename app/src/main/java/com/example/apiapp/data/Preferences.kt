package com.example.apiapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
private const val EMAIL_PREF = "EMAIL"
private const val RANGE_PREF = "RANGE_MAP"
class Preferences(val context: Context) {
    private val preferences = context.getSharedPreferences("Preferences", MODE_PRIVATE)

    fun getEmail():String{
        return preferences.getString(EMAIL_PREF," ")!!
    }

    fun setEmail(arg: String){
        preferences.edit().putString(EMAIL_PREF,arg).apply()
    }

    fun setRange(arg: Int){
        preferences.edit().putInt(RANGE_PREF,arg).apply()
    }

    fun getRange():Int{
        return preferences.getInt(RANGE_PREF,50)
    }
}