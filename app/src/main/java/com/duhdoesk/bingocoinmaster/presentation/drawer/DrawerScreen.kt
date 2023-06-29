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
        is DrawerState.Loading -> LoadingScreen()
        is DrawerState.Ready -> ReadyScreen()
        is DrawerState.Drawing -> DrawingScreen(state = state as DrawerState.Drawing)
        else -> FinishedScreen(state = state as DrawerState.Finished)
    }
}

@Composable
fun LoadingScreen() {
    Text(text = "Loading")
}

@Composable
fun ReadyScreen() {
    Text(text = "Ready")
}

@Composable
fun DrawingScreen(state: DrawerState.Drawing) {
    Text(text = "Drawing")
}

@Composable
fun FinishedScreen(state: DrawerState.Finished) {
    Text(text = "Finished")
}