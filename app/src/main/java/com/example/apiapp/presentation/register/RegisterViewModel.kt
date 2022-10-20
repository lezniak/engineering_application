package com.example.apiapp.presentation.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.objects.User
import com.example.apiapp.data.repository.implementation.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {
    private val newUser: User = User("1999.11.11","Katowice","","","","","+48000000000")

    fun setBirthDate(arg: LocalDate){
        newUser.birthdate = arg.toString().replace("-",".")
    }
    fun setEmail(arg:String){
        newUser.email = arg
    }
    fun setPassword(arg:String){
        newUser.password = arg
        newUser.matchingPassword =arg
    }
    fun setPhone(arg:String){
        newUser.phoneNumber = arg
    }
    fun setName(arg:String){
        newUser.name = arg
    }
    private var _result = MutableLiveData<Int>(0)
    var result: LiveData<Int> = _result

    fun finishRegister(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerUser(newUser).enqueue(object : Callback<ServiceReturn<User>> {
                override fun onResponse(
                    call: Call<ServiceReturn<User>>,
                    response: Response<ServiceReturn<User>>
                ) {
                    val res = response.body()
                    if (res != null) {
                        if (res.errList.isNotEmpty()){
                            res.errList.forEach {
                                Log.e("Errors",it.key)
                            }
                        }
                    }

                    _result.value = response.body()!!.status
                    call.cancel()

                }

                override fun onFailure(call: Call<ServiceReturn<User>>, t: Throwable) {
                    Log.e("VIEWMODEL","NIE UDALO SIE WYSSLAC"+t.toString())
                    Log.e("RESQUESR",call.request().toString())
                }

            })
        }
    }
}