package com.duhdoesk.bingocoinmaster.presentation.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.duhdoesk.bingocoinmaster.R
import com.duhdoesk.bingocoinmaster.model.Character
import com.duhdoesk.bingocoinmaster.presentation.components.CommonLoadingScreen


@Composable
fun DrawnCharactersScreen(
    viewModel: DrawerViewModel = hiltViewModel()
) {
    when (val state = viewModel.drawnCharactersState.collectAsState().value) {
        is DrawnCharactersState.Loading -> CommonLoadingScreen()
        is DrawnCharactersState.Ready -> DrawnCharacterLazyGrid(
            state.drawnCharacters.sortedBy { it.charId.toInt() })
    }
}

@Composable
fun DrawnCharacterLazyGrid(drawnCharacters: List<Character>) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.drawnCharacters),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            for (character in drawnCharacters) {
                item {
                    DrawnCharacterCard(character = character)
                }
            }
        }
    }
}

@Composable
fun DrawnCharacterCard(character: Character) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(60.dp)
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
                modifier = Modifier.fillMaxWidth(0.3f)
            )

            Text(
                text = character.name,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(start = 8.dp)
            )

            Text(
                text = "#${character.charId}",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(end = 16.dp)
            )
        }
    }
}