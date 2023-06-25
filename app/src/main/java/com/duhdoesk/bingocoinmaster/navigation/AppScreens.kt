package com.duhdoesk.bingocoinmaster.navigation

enum class AppScreens() {
    DrawerScreen,
    CharacterScreen;

    companion object {
        fun fromRoute(route: String?): AppScreens = when (route?.substringBefore("/")) {
            DrawerScreen.name -> DrawerScreen
            CharacterScreen.name -> CharacterScreen
            null -> DrawerScreen
            else -> throw IllegalArgumentException("Route $route is not reachable.")
        }
    }
}