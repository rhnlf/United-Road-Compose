package com.rhnlf.unitedroadcompose.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhnlf.unitedroadcompose.R

@Composable
fun ProfileScreen() {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.about_page))
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            elevation = 0.dp
        )
    }, content = {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .fillMaxWidth(),

                ) {
                ProfileContent()
            }
        }
    })
}

@Composable
fun ProfileContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier.size(200.dp),
            shape = CircleShape,
            elevation = 3.dp,
        ) {
            Image(
                painterResource(R.drawable.pas_foto),
                contentDescription = stringResource(R.string.about_page),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(R.string.name),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
        )
        Spacer(modifier = Modifier.size(3.dp))
        Text(
            text = stringResource(R.string.email),
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
        )
    }
}