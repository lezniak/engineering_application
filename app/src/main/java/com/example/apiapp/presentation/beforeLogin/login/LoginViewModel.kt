package com.example.apiapp.presentation.beforeLogin.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.common.errorList
import com.example.apiapp.data.objects.LoginUser
import com.example.apiapp.data.objects.ServiceReturn
import com.example.apiapp.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {
    val loginUser : LoginUser = LoginUser(0,"","","","","","","")
    private var _loginResult = MutableLiveData<String>("")
    var loginResult: LiveData<String> = _loginResult

    private var _loginSuccess = MutableLiveData<LoginUser>()
    var loginSuccess: LiveData<LoginUser> = _loginSuccess

    fun setLoginResult(arg: String){
        _loginResult.value = arg
    }
    fun loginToService(){
        viewModelScope.launch {
            repository.loginUser(loginUser).enqueue(object : Callback<ServiceReturn<LoginUser>> {
                override fun onResponse(
                    call: Call<ServiceReturn<LoginUser>>,
                    response: Response<ServiceReturn<LoginUser>>
                ) {
                    if (response.body()?.status == 1){
                        _loginSuccess.value = response.body()?.value!!
                    }
                    if (response.body() != null){
                        response.body()?.errList?.errorList()

                        response.body()?.errList?.forEach {
                            _loginResult.value = it.value
                        }
                    }
                }

                override fun onFailure(call: Call<ServiceReturn<LoginUser>>, t: Throwable) {
                    Log.d("Login_response_fail",t.stackTraceToString())
                }
            })
        }
    }
}