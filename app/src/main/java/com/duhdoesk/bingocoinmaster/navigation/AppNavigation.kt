package com.duhdoesk.bingocoinmaster.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duhdoesk.bingocoinmaster.presentation.character.CharacterScreen
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawerScreen
import com.duhdoesk.bingocoinmaster.presentation.drawer.LoadingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.DrawerScreen.name) {
        composable(AppScreens.DrawerScreen.name) {
            DrawerScreen(navController = navController)
        }

        composable(AppScreens.CharacterScreen.name) {
            CharacterScreen(navController)
        }
    }
}