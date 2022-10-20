package com.example.apiapp.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.common.errorList
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.repository.implementation.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {
    val loginUser : LoginUser = LoginUser("","","")

    fun loginToService(){
        viewModelScope.launch {
            repository.loginUser(loginUser).enqueue(object : Callback<ServiceReturn<LoginUser>> {
                override fun onResponse(
                    call: Call<ServiceReturn<LoginUser>>,
                    response: Response<ServiceReturn<LoginUser>>
                ) {
                    Log.d("Login_reposnse", response.body()?.status.toString())
                    if (response.body() != null)
                        response.body()?.errList?.errorList()

                }

                override fun onFailure(call: Call<ServiceReturn<LoginUser>>, t: Throwable) {
                    Log.d("Login_response_fail",t.stackTraceToString())
                }
            })
        }
    }
}