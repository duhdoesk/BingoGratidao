package com.duhdoesk.bingocoinmaster.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duhdoesk.bingocoinmaster.presentation.character.CharacterScreen
import com.duhdoesk.bingocoinmaster.presentation.card.CardScreen
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawerScreen
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawnCharactersScreen
import com.duhdoesk.bingocoinmaster.presentation.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.name) {

        composable(AppScreens.DrawerScreen.name) {
            DrawerScreen(navController = navController)
        }

        composable(AppScreens.CardScreen.name) {
            CardScreen(navController = navController)
        }

        composable("${AppScreens.CharacterScreen.name}/{cardId}") { navBackStackEntry ->
            val cardId = navBackStackEntry.arguments?.getString("cardId")
            cardId?.let {
                CharacterScreen(it)
            }
        }

        composable(AppScreens.DrawnCharactersScreen.name) {
            DrawnCharactersScreen()
        }

        composable(AppScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
    }
}