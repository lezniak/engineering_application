package com.example.apiapp.presentation.afterLogin.map

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() :  ViewModel()  {
    var range = 50f


}