package com.duhdoesk.bingocoinmaster.presentation.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.duhdoesk.bingocoinmaster.R
import com.duhdoesk.bingocoinmaster.presentation.components.BingoCard
import com.duhdoesk.bingocoinmaster.presentation.components.CommonLoadingScreen
import com.duhdoesk.bingocoinmaster.presentation.components.CommonReadyScreen

@Composable
fun CardScreen(
    navController: NavController,
    viewModel: CardViewModel = hiltViewModel()
) {

    when (val state = viewModel.state.collectAsState().value) {
        CardDrawState.Loading -> CommonLoadingScreen()

        CardDrawState.Ready -> CommonReadyScreen(
            onClick = { viewModel.sortNewBingoCard() }
        )

        else -> CardDrawingScreen(
            state = state as CardDrawState.Drawn,
            onButtonClick = { viewModel.sortNewBingoCard() },
            navController = navController
        )
    }
}

@Composable
fun CardDrawingScreen(
    state: CardDrawState.Drawn,
    onButtonClick: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BingoCard(
            state = state,
            navController = navController
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 4.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                text = stringResource(id = R.string.name).uppercase(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            )
        }

        Button(
            onClick = { onButtonClick() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(id = R.string.new_card_button))
        }
    }
}