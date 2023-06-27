package com.duhdoesk.bingocoinmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.duhdoesk.bingocoinmaster.navigation.AppNavigation
import com.duhdoesk.bingocoinmaster.ui.theme.BingoCoinMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingoCoinMasterTheme {
                Surface {
                    BingoApp()
                }
            }
        }
    }
}

@Composable
fun BingoApp() {
    AppNavigation()
}