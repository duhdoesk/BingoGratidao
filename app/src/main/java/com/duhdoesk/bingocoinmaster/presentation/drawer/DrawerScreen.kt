package com.duhdoesk.bingocoinmaster.presentation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.duhdoesk.bingocoinmaster.R
import com.duhdoesk.bingocoinmaster.model.Character
import com.duhdoesk.bingocoinmaster.navigation.AppScreens
import com.duhdoesk.bingocoinmaster.presentation.components.CommonLoadingScreen
import com.duhdoesk.bingocoinmaster.presentation.components.CommonReadyScreen

@Composable
fun DrawerScreen(
    viewModel: DrawerViewModel = hiltViewModel(),
    navController: NavHostController
) {
    when (val state = viewModel.state.collectAsState().value) {
        is DrawerState.Loading -> CommonLoadingScreen()

        is DrawerState.Ready -> CommonReadyScreen(
            onClick = { viewModel.startNewDrawing() }
        )

        is DrawerState.Drawing -> DrawingScreen(state = state, navController)

        is DrawerState.Finished -> FinishedScreen(state = state, navController)
    }
}

@Composable
fun ActiveSessionScreen(
    state: DrawerState,
    character: Character,
    navController: NavHostController,
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
                        .data(character.picture)
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
                text = character.name,
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

            if (state is DrawerState.Drawing) {
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
                    modifier = Modifier.width(200.dp).padding(top = 4.dp)
                ) {
                    Text(text = stringResource(id = R.string.finishDrawing))
                }
            } else {
                Text(text = stringResource(id = R.string.allCharactersDrawn))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(top = 4.dp).defaultMinSize(minWidth = 200.dp)
                ) {
                    Text(text = stringResource(id = R.string.home))
                }
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

            LazyRow(
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.clickable {
                    navController.navigate(AppScreens.DrawnCharactersScreen.name)
                }
            ) {
                for (c in viewModel.getDrawnCharacters().reversed()) {
                    item {
                        Card(
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =
                                MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Text(
                                text = c.name,
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(stringResource(id = R.string.finishDrawing)) },
                text = { Text(stringResource(id = R.string.finishDrawingConfirmation)) },
                confirmButton = {
                    Button(onClick = { viewModel.finishDrawing() }) {
                        Text(stringResource(id = R.string.confirm).uppercase())
                    }
                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.secondary
                        ),
                        onClick = { showDialog.value = false }
                    ) {
                        Text(stringResource(id = R.string.cancel).uppercase())
                    }
                }
            )
        }
    }
}

@Composable
fun FinishedScreen(state: DrawerState.Finished, navController: NavHostController) {
    ActiveSessionScreen(
        state = state,
        character = state.lastCharacter,
        navController = navController
    )
}

@Composable
fun DrawingScreen(state: DrawerState.Drawing, navController: NavHostController) {
    ActiveSessionScreen(
        state = state,
        character = state.lastCharacter,
        navController = navController
    )
}