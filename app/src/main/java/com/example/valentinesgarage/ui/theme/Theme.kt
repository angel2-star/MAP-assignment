package com.example.valentinesgarage.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GarageRed = Color(0xFFD32F2F)
private val GarageDarkRed = Color(0xFF9A0007)
private val GarageBlack = Color(0xFF1A1A1A)
private val GarageWhite = Color(0xFFFFFFFF)
private val GarageSurface = Color(0xFFF5F5F5)

private val BlackRedColors = lightColorScheme(
    primary = GarageBlack,
    onPrimary = GarageWhite,
    primaryContainer = Color(0xFF333333),
    onPrimaryContainer = GarageWhite,
    secondary = GarageRed,
    onSecondary = GarageWhite,
    secondaryContainer = GarageDarkRed,
    onSecondaryContainer = GarageWhite,
    background = GarageWhite,
    onBackground = GarageBlack,
    surface = GarageSurface,
    onSurface = GarageBlack,
    error = Color(0xFFB00020),
    onError = GarageWhite
)

@Composable
fun ValentinesGarageTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BlackRedColors,
        content = content
    )
}