package com.example.apiapp.presentation.afterLogin.profile

import androidx.lifecycle.ViewModel
import com.example.apiapp.data.objects.Setting
import javax.inject.Inject

class ProfileViewModel @Inject constructor(): ViewModel() {
    val settings = listOf(
        Setting("",""),
        Setting("Zakupione bilety","Zobacz swoje zakupione bilety"),
        Setting("Wydarzenia","Zobacz wydarzenia w których uczestnicznysz"),
        Setting("Archiwum wydarzeń","Zobacz wydarzenia które już się skonczyły"),
        Setting("Ustaw odległość","Ustaw interesującą dla Ciebie ogległość do wydarzeń"),
        Setting("",""),
        Setting("Zmień hasło","Zmień hasło do swojego konta"),
        Setting("Wyloguj się","")
    )
}