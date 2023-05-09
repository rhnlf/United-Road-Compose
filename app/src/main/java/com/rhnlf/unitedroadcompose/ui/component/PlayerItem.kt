package com.rhnlf.unitedroadcompose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rhnlf.unitedroadcompose.ui.theme.UnitedRoadTheme

@Composable
fun PlayerItem(
    name: String,
    position: String,
    image: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
                Text(
                    text = position,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlayerItemPreview() {
    UnitedRoadTheme {
        PlayerItem("David de Gea", "Goalkeeper", "")
    }
}