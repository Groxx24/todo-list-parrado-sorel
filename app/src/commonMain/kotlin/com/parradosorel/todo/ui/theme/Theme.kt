package com.parradosorel.todo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Teal with a coral accent, light or dark following the phone. Every role is set in both schemes,
 * so nothing falls back to Material's purple baseline. The window background in
 * `androidMain/res/values{,-night}` is [LightColors]' and [DarkColors]' background, so keep them in step.
 */
@Composable
fun OurListsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColors else LightColors, content = content)
}

private val LightColors = lightColorScheme(
    primary = Color(0xFF00696B),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF9CF1F2),
    onPrimaryContainer = Color(0xFF002020),
    inversePrimary = Color(0xFF80D4D5),
    secondary = Color(0xFF4A6363),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCCE8E7),
    onSecondaryContainer = Color(0xFF051F1F),
    tertiary = Color(0xFFA43D2E),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDAD4),
    onTertiaryContainer = Color(0xFF410000),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF4FBFA),
    onBackground = Color(0xFF161D1D),
    surface = Color(0xFFF4FBFA),
    onSurface = Color(0xFF161D1D),
    surfaceVariant = Color(0xFFDAE5E4),
    onSurfaceVariant = Color(0xFF3F4948),
    surfaceTint = Color(0xFF00696B),
    inverseSurface = Color(0xFF2B3231),
    inverseOnSurface = Color(0xFFECF2F1),
    outline = Color(0xFF6F7979),
    outlineVariant = Color(0xFFBEC9C8),
    scrim = Color.Black,
    surfaceBright = Color(0xFFF4FBFA),
    surfaceDim = Color(0xFFD5DBDA),
    // Cards are white on the off-white background.
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color.White,
    surfaceContainer = Color(0xFFE9EFEE),
    surfaceContainerHigh = Color(0xFFE3E9E9),
    surfaceContainerHighest = Color(0xFFDDE4E3),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF80D4D5),
    onPrimary = Color(0xFF003737),
    primaryContainer = Color(0xFF004F50),
    onPrimaryContainer = Color(0xFF9CF1F2),
    inversePrimary = Color(0xFF00696B),
    secondary = Color(0xFFB0CCCB),
    onSecondary = Color(0xFF1B3534),
    secondaryContainer = Color(0xFF324B4B),
    onSecondaryContainer = Color(0xFFCCE8E7),
    tertiary = Color(0xFFFFB4A7),
    onTertiary = Color(0xFF640D05),
    tertiaryContainer = Color(0xFF842519),
    onTertiaryContainer = Color(0xFFFFDAD4),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0E1515),
    onBackground = Color(0xFFDDE4E3),
    surface = Color(0xFF0E1515),
    onSurface = Color(0xFFDDE4E3),
    surfaceVariant = Color(0xFF3F4948),
    onSurfaceVariant = Color(0xFFBEC9C8),
    surfaceTint = Color(0xFF80D4D5),
    inverseSurface = Color(0xFFDDE4E3),
    inverseOnSurface = Color(0xFF2B3231),
    outline = Color(0xFF889392),
    outlineVariant = Color(0xFF3F4948),
    scrim = Color.Black,
    surfaceBright = Color(0xFF343B3A),
    surfaceDim = Color(0xFF0E1515),
    surfaceContainerLowest = Color(0xFF090F0F),
    surfaceContainerLow = Color(0xFF161D1D),
    surfaceContainer = Color(0xFF1A2121),
    surfaceContainerHigh = Color(0xFF252B2B),
    surfaceContainerHighest = Color(0xFF2F3636),
)
