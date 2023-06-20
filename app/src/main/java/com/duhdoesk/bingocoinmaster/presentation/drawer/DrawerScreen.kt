package com.duhdoesk.bingocoinmaster.presentation.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.duhdoesk.bingocoinmaster.R
import com.duhdoesk.bingocoinmaster.presentation.components.BingoCard

@Composable
fun DrawingScreen(
    state: DrawState.Drawn,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BingoCard(state = state)

        Text(
            text = stringResource(id = R.string.name).uppercase(),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 4.dp)
        )

        Button(
            onClick = { onClick() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(id = R.string.new_card_button))
        }
    }
}

@Composable
fun ReadyScreen(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.all_ready))

        Button(
            onClick = { onClick() },
            modifier = Modifier
                .width(160.dp)
                .padding(top = 16.dp)
        ) {
            Text(stringResource(id = R.string.start_button))
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.loading_data))
    }
}