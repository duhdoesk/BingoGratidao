package com.duhdoesk.bingocoinmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawState
import com.duhdoesk.bingocoinmaster.presentation.drawer.LoadingScreen
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawerViewModel
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawingScreen
import com.duhdoesk.bingocoinmaster.presentation.drawer.ReadyScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<DrawerViewModel>()
            BingoApp(viewModel)
        }
    }
}

@Composable
fun BingoApp(viewModel: DrawerViewModel) {

    when (val state = viewModel.state.collectAsState().value) {
        is DrawState.Loading -> {
            LoadingScreen()
        }

        is DrawState.Ready -> {
            ReadyScreen(
                onClick = { viewModel.sortNewBingoCard() }
            )
        }

        else -> {
            DrawingScreen(state = state as DrawState.Drawn)
        }
    }
}