package com.duhdoesk.bingocoinmaster.presentation.drawer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DrawerScreen(
    viewModel: DrawerViewModel = hiltViewModel()
) {
    when (val state = viewModel.state.collectAsState().value) {
        DrawerState.Loading -> LoadingScreen()
        else -> DrawingScreen(state = state as DrawerState.Drawing)
    }
}

@Composable
fun LoadingScreen() {
    Text(text = "Loading")
}

@Composable
fun DrawingScreen(state: DrawerState.Drawing) {
    Text(text = "Drawing")
}