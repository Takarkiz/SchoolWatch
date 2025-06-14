package org.khaki.schoolwatch.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Dracula theme color palette
object DraculaColors {
    val Background = Color(0xFF282A36)
    val CurrentLine = Color(0xFF44475A)
    val Foreground = Color(0xFFF8F8F2)
    val Comment = Color(0xFF6272A4)
    val Cyan = Color(0xFF8BE9FD)
    val Green = Color(0xFF50FA7B)
    val Orange = Color(0xFFFFB86C)
    val Pink = Color(0xFFFF79C6)
    val Purple = Color(0xFFBD93F9)
    val Red = Color(0xFFFF5555)
    val Yellow = Color(0xFFF1FA8C)
}

// Dark color scheme based on Dracula theme
val DraculaDarkColorScheme = darkColorScheme(
    primary = DraculaColors.Purple,
    onPrimary = DraculaColors.Foreground,
    primaryContainer = DraculaColors.CurrentLine,
    onPrimaryContainer = DraculaColors.Foreground,

    secondary = DraculaColors.Pink,
    onSecondary = DraculaColors.Foreground,
    secondaryContainer = DraculaColors.CurrentLine,
    onSecondaryContainer = DraculaColors.Foreground,

    tertiary = DraculaColors.Cyan,
    onTertiary = DraculaColors.Background,
    tertiaryContainer = DraculaColors.Comment,
    onTertiaryContainer = DraculaColors.Foreground,

    error = DraculaColors.Red,
    onError = DraculaColors.Foreground,
    errorContainer = DraculaColors.Red.copy(alpha = 0.3f),
    onErrorContainer = DraculaColors.Foreground,

    background = DraculaColors.Background,
    onBackground = DraculaColors.Foreground,
    surface = DraculaColors.Background,
    onSurface = DraculaColors.Foreground,

    surfaceVariant = DraculaColors.CurrentLine,
    onSurfaceVariant = DraculaColors.Foreground,
    outline = DraculaColors.Comment,
    outlineVariant = DraculaColors.Comment.copy(alpha = 0.5f)
)

// Light color scheme with Dracula-inspired colors (for completeness)
val DraculaLightColorScheme = lightColorScheme(
    primary = DraculaColors.Purple,
    onPrimary = Color.White,
    primaryContainer = DraculaColors.Purple.copy(alpha = 0.3f),
    onPrimaryContainer = DraculaColors.Purple,

    secondary = DraculaColors.Pink,
    onSecondary = Color.White,
    secondaryContainer = DraculaColors.Pink.copy(alpha = 0.3f),
    onSecondaryContainer = DraculaColors.Pink,

    tertiary = DraculaColors.Cyan,
    onTertiary = Color.Black,
    tertiaryContainer = DraculaColors.Cyan.copy(alpha = 0.3f),
    onTertiaryContainer = DraculaColors.Cyan,

    error = DraculaColors.Red,
    onError = Color.White,
    errorContainer = DraculaColors.Red.copy(alpha = 0.1f),
    onErrorContainer = DraculaColors.Red,

    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,

    surfaceVariant = Color(0xFFF2F2F2),
    onSurfaceVariant = Color.DarkGray,
    outline = Color.Gray,
    outlineVariant = Color.LightGray
)

// Composable function to provide the Dracula theme
@Composable
fun DraculaTheme(
    darkTheme: Boolean = true, // Default to dark theme since Dracula is primarily a dark theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DraculaDarkColorScheme else DraculaLightColorScheme

    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}