package com.duhdoesk.bingocoinmaster.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duhdoesk.bingocoinmaster.presentation.character.CharacterScreen
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawerScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.DrawerScreen.name) {
        composable(AppScreens.DrawerScreen.name) {
            DrawerScreen(navController = navController)
        }

        composable("${AppScreens.CharacterScreen.name}/{cardId}") { navBackStackEntry ->
            val cardId = navBackStackEntry.arguments?.getString("cardId")
            cardId?.let {
                CharacterScreen(it)
            }
        }
    }
}