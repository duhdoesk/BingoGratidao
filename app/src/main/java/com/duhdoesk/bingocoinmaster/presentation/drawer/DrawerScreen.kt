package com.duhdoesk.bingocoinmaster.presentation.drawer

import android.app.AlertDialog
import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.duhdoesk.bingocoinmaster.R
import com.duhdoesk.bingocoinmaster.presentation.components.CommonLoadingScreen
import com.duhdoesk.bingocoinmaster.presentation.components.CommonReadyScreen
import com.duhdoesk.bingocoinmaster.presentation.components.DrawnCharactersLazyRow

@Composable
fun DrawerScreen(
    viewModel: DrawerViewModel = hiltViewModel()
) {
    when (val state = viewModel.state.collectAsState().value) {
        is DrawerState.Loading -> CommonLoadingScreen()
        is DrawerState.Ready -> CommonReadyScreen(
            onClick = { viewModel.startNewDrawing() }
        )

        is DrawerState.Drawing -> DrawingScreen(state = state)
        else -> FinishedScreen(state = state as DrawerState.Finished)
    }
}

@Composable
fun DrawingScreen(
    state: DrawerState.Drawing,
    viewModel: DrawerViewModel = hiltViewModel()
) {
    val showDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            Text(
                text = stringResource(id = R.string.screen_title),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )

            Text(
                text =
                "${viewModel.getAmounts().first} / ${viewModel.getAmounts().second}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Card(
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(horizontal = 16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.lastCharacter.picture)
                        .crossfade(true)
                        .scale(Scale.FILL)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Character Picture",
                    placeholder = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = state.lastCharacter.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Button(
                onClick = { viewModel.drawNextCharacter() },
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = stringResource(id = R.string.nextCharacter))
            }

            Button(
                onClick = { showDialog.value = !showDialog.value },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = stringResource(id = R.string.finishDrawing))
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.drawnCharacters),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            DrawnCharactersLazyRow(drawnList = viewModel.getDrawnCharacters().reversed())
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(stringResource(id = R.string.finishDrawing)) },
                text = { Text(stringResource(id = R.string.finishDrawingConfirmation)) },
                confirmButton = {
                    Button(onClick = { viewModel.finishDrawing() }) {
                        Text("Sim".uppercase())
                    }
                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.secondary
                        ),
                        onClick = { showDialog.value = false }
                    ) {
                        Text("Cancelar".uppercase())
                    }
                },
            )
        }
    }
}

@Composable
fun FinishedScreen(state: DrawerState.Finished) {
    Text(text = "Finished")
}