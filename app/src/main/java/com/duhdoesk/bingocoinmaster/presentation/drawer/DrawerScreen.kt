package com.duhdoesk.bingocoinmaster.presentation.drawer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.duhdoesk.bingocoinmaster.presentation.components.CommonLoadingScreen
import com.duhdoesk.bingocoinmaster.presentation.components.CommonReadyScreen

@Composable
fun DrawerScreen(
    viewModel: DrawerViewModel = hiltViewModel()
) {
    when (val state = viewModel.state.collectAsState().value) {
        is DrawerState.Loading -> CommonLoadingScreen()
        is DrawerState.Ready -> CommonReadyScreen(
            onClick = { viewModel.startNewDrawing() }
        )
        is DrawerState.NotStarted -> NotStartedScreen()
        is DrawerState.Drawing -> DrawingScreen(state = state as DrawerState.Drawing)
        else -> FinishedScreen(state = state as DrawerState.Finished)
    }
}

@Composable
fun NotStartedScreen() {
    Text(text = "Not Started")
}

@Composable
fun DrawingScreen(state: DrawerState.Drawing) {
    Text(text = "Drawing")
}

@Composable
fun FinishedScreen(state: DrawerState.Finished) {
    Text(text = "Finished")
}