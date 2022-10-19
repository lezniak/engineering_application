package com.example.apiapp.presentation.register

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val newUser: User = User("1999.11.11","Katowice","test@o2.pl","daniel1","daniel","daniel1","+48111111111")

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

    fun finishRegister(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerUser(newUser).enqueue(object : Callback<ServiceReturn<User>> {
                override fun onResponse(
                    call: Call<ServiceReturn<User>>,
                    response: Response<ServiceReturn<User>>
                ) {
                    Log.e("UDALO SIE","wyslac")
                    val test = response.isSuccessful
                    val test2 = response.body()
                }

                override fun onFailure(call: Call<ServiceReturn<User>>, t: Throwable) {
                    Log.e("VIEWMODEL","NIE UDALO SIE WYSSLAC"+t.toString())
                    Log.e("RESQUESR",call.request().toString())
                }

            })
//            repository.test().enqueue(object: Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    Log.d("DZIALA",response.body()!!)
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Log.d("NDZIALA",t.toString())
//                }
//
//            });
        }
    }
}