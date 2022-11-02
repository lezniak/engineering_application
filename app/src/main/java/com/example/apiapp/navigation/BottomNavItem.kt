package com.example.apiapp.navigation

import com.example.apiapp.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home_24,"home")
    object Map: BottomNavItem("Map",R.drawable.ic_baseline_map_24,"map")
    object Events : BottomNavItem("Events",R.drawable.ic_baseline_event_24,"events")
    object Profile : BottomNavItem("Profile",R.drawable.ic_baseline_person_24,"profile")
}
