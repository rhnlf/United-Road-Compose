package com.rhnlf.unitedroadcompose.ui.screen.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.rhnlf.unitedroadcompose.R
import com.rhnlf.unitedroadcompose.di.Injection
import com.rhnlf.unitedroadcompose.ui.ViewModelFactory
import com.rhnlf.unitedroadcompose.ui.common.UiState

@Composable
fun DetailScreen(
    playerId: Int,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPlayerById(playerId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.name,
                    data.position,
                    data.nationality,
                    data.birthDate,
                    data.biography,
                    data.image,
                    R.drawable.background,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    position: String,
    nationality: String,
    birthDate: String,
    biography: String,
    image: String,
    @DrawableRes background: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            Image(
                painterResource(background),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .alpha(0.9f)
                    .height(240.dp)
                    .fillMaxWidth()
            )
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
        }

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                )
                Text(
                    text = position,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                )
                Text(
                    text = nationality,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                )
                Text(
                    text = birthDate,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 8.dp),
                )
            }
        }

        Text(
            text = "Biography",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
        )

        Text(
            text = biography,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 4.dp),
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DetailScreenPreview() {
    DetailContent(
        "Marcus Rashford",
        "Forward",
        "England",
        "31/10/1997",
        "Marcus Rashford (born 31 October 1997) is an English professional footballer who plays as a forward for Premier League club Manchester United and the England national team. A Manchester United player from the age of seven, he scored two goals on both his first-team debut against Midtjylland in the UEFA Europa League in February 2016 and his Premier League debut against Arsenal three days later.",
        "",
        R.drawable.background,
    )
}