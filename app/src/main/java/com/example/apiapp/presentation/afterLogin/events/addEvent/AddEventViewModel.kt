package com.example.apiapp.presentation.afterLogin.events.addEvent

import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.apiapp.data.Preferences
import com.example.apiapp.data.objects.Dao.EventAddressDto
import com.example.apiapp.data.objects.Dao.EventDao
import com.example.apiapp.data.useCase.EventsState
import com.example.apiapp.presentation.activity.AfterLoginActivity
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(application: Application) : ViewModel() {
    var lati = Preferences(application).getLat()
    var long = Preferences(application).getLong()
    var eventPosition = LatLng(0.0,0.0)
    private val _name = mutableStateOf<String>("")
    val name: State<String> = _name

    private val _descripton = mutableStateOf<String>("")
    val descripton: State<String> = _descripton

    private val _startDate = mutableStateOf<String>("")
    val startDate: State<String> = _startDate

    private val _maxMembers = mutableStateOf<String>("")
    val maxMembers: State<String> = _maxMembers

    private val _forAll = mutableStateOf(false)
    val forAll: State<Boolean> = _forAll

    private val _generateTicket = mutableStateOf(false)
    val generateTicket: State<Boolean> = _generateTicket

    private val _city = mutableStateOf("")
    val city: State<String> = _city

    private val _street = mutableStateOf("")
    val street: State<String> = _street


    fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double,context: Context): List<String> {
        var strAdd = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.getMaxAddressLineIndex()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.w("My Current loction address", strReturnedAddress.toString())
            } else {
                Log.w("My Current loction address", "No Address returned!")
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.w("My Current loction address", "Canont get Address!")
        }
        return strAdd.split(",")
    }

    fun setStreet(street: String){
        _street.value = street
    }

    fun setCity(city: String){
        _city.value = city
    }

    fun setName(name: String){
        _name.value = name
    }

    fun setDesc(desc: String){
        _descripton.value = desc
    }

    fun setStartDate(date: String){
        _startDate.value = date
    }

    fun setMaxMembers(maxMembers: String){
        _maxMembers.value = maxMembers
    }

    fun setForall(isForAll: Boolean){
        _forAll.value = isForAll
    }

    fun setGenerateTicket(isGenerateTicket: Boolean){
        _forAll.value = isGenerateTicket
    }
}