package com.rhnlf.unitedroadcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = red,
    primaryVariant = dark_red,
    onPrimary = black,
    secondary = blue_grey,
    secondaryVariant = grey,
    onSecondary = black
)

private val LightColorPalette = lightColors(
    primary = red,
    primaryVariant = dark_red,
    onPrimary = white,
    secondary = blue_grey,
    secondaryVariant = grey,
    onSecondary = black,
)

@Composable
fun UnitedRoadTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors, typography = Typography, shapes = Shapes, content = content
    )
}