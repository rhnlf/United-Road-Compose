package com.rhnlf.unitedroadcompose.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rhnlf.unitedroadcompose.R

@Composable
fun PlayerNotFound(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.player_not_found),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}