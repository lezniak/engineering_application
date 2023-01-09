package com.example.apiapp.presentation.beforeLogin.register

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiapp.common.errorList
import com.example.apiapp.data.objects.Results.DataValidation
import com.example.apiapp.data.objects.Results.ServiceReturn
import com.example.apiapp.data.objects.User
import com.example.apiapp.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {
    private val newUser: User = User("1999.11.11","Katowice","","","","","+48000000000")
    val nameDV = DataValidation<String>("Imie")
    val emailDV = DataValidation<String>("E-mail@a.b")
    val passwordDV = DataValidation<String>("PASSWO@1asd")
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
                        res.errList.errorList()

                    _result.value = response.body()!!.status
                    call.cancel()

                } }

                override fun onFailure(call: Call<ServiceReturn<User>>, t: Throwable) {
                    Log.e("VIEWMODEL","NIE UDALO SIE WYSSLAC"+t.toString())
                    Log.e("RESQUESR",call.request().toString())
                }

            })
        }
    }

    fun validateName(name: String): Boolean{
        if (name.length<3 || name.length>15)
            return true
        else
            return false
    }

    fun validationEmail(email: String): Boolean{
        if (TextUtils.isEmpty(email) || email.length>50) {
            return true
        } else {
            return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }


    fun validationPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,20}$"
        pattern = Pattern.compile(PASSWORD_REGEX)
        matcher = pattern.matcher(password)
        return !matcher.matches()
    }
}