package com.duhdoesk.bingocoinmaster.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.duhdoesk.bingocoinmaster.R
import com.duhdoesk.bingocoinmaster.model.Character
import com.duhdoesk.bingocoinmaster.navigation.AppScreens
import com.duhdoesk.bingocoinmaster.presentation.drawer.DrawState

@Composable
fun BingoCard(
    state: DrawState.Drawn,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(Modifier.padding(horizontal = 4.dp)) {
            Text(
                text = stringResource(id = R.string.screen_title),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Row(Modifier.fillMaxWidth()) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .height(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hearts),
                        contentDescription = "Hearts",
                        modifier = Modifier.size(40.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(80.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.card_number),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = state.counter.toString(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hearts),
                        contentDescription = "Hearts",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

        }

        BingoLazyGrid(state.characterList, navController)
    }
}

@Composable
fun BingoLazyGrid(
    characters: List<Character>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), userScrollEnabled = false
    ) {
        for (card in characters) {
            item {
                BingoStone(
                    character = card,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                    navController
                )
            }
        }
    }
}

@Composable
fun BingoStone(
    character: Character,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.clickable {
                navController.navigate(
                    "${AppScreens.CharacterScreen.name}/${character.charId}"
                )
            }
        ) {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier.height(140.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.picture)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Stone Picture",
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hexagon),
                        contentDescription = "Hexagon",
                        modifier = Modifier.size(40.dp)
                    )

                    Text(
                        text = character.charId,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .height(40.dp)
                )
            }
        }
    }
}