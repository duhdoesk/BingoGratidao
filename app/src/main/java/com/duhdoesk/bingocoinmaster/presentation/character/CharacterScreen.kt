package com.duhdoesk.bingocoinmaster.presentation.character

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CharacterScreen(
    navController: NavController,
    cardId: String
) {
    Text(text = cardId)
}