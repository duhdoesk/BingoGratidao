package com.duhdoesk.bingocoinmaster.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duhdoesk.bingocoinmaster.R

@Composable
fun BingoCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BingoLazyGrid()

        Column(Modifier.padding(vertical = 8.dp, horizontal = 4.dp)) {
            Text(
                text = "Bingo da Gratidão",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Cartela nº",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
        }

        BingoLazyGrid()
    }
}

@Composable
fun BingoLazyGrid() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp)
    ) {
        item { BingoStone(Modifier.padding(4.dp)) }
        item { BingoStone(Modifier.padding(4.dp)) }
        item { BingoStone(Modifier.padding(4.dp)) }
    }
}

@Composable
fun BingoStone(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Box(contentAlignment = Alignment.BottomStart) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Inside,
                    contentDescription = "Stone Picture",
                    modifier = Modifier.fillMaxWidth()
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
                        text = "24",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }

            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "Torta de Limão",
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BingoCardPreview() {
    BingoCard()
}

@Preview
@Composable
fun BingoStonePreview() {
    BingoStone()
}