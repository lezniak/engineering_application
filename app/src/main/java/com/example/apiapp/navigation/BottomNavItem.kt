package com.example.apiapp.navigation

import com.example.apiapp.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    //Main Screen
    object Home : BottomNavItem("Strona główna", R.drawable.ic_baseline_home_24,"home")
    object Map: BottomNavItem("Mapa",R.drawable.ic_baseline_map_24,"map")
    object Events : BottomNavItem("Wydarzenia",R.drawable.ic_baseline_event_24,"events")
    object Profile : BottomNavItem("Profil",R.drawable.ic_baseline_person_24,"profile")

    object AddEvent : BottomNavItem("AddEvent",R.drawable.ic_baseline_person_24,"add_event")
    object Event: BottomNavItem("Event",R.drawable.ic_baseline_event_24,"event")
    object EventAccept: BottomNavItem("EventAccept",R.drawable.ic_baseline_event_24,"event_accept")


}
