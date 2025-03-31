package space.song.agentrtw.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(

    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 30.sp,
        color = TextLight
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp,
        color = TextLight
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 26.sp,
        color = TextLight
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp,
        color = TextLight
    ),

    // Bold 14 px
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp,
        color = TextLight
    ),
    // Bold 12 px
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        color = TextLight
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        color = TextLight
    ),
    bodyMedium = TextStyle( // Regular Body 1
        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        color = TextLight
    ),
    bodySmall = TextStyle( // Regular Body 2
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 14.sp,
        color = TextLight
    ),
    labelLarge = TextStyle( // Regular Button
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 14.sp,
        color = TextLight
    ),
    labelMedium = TextStyle(  // Prompt Text
        fontFamily = FontFamily.Default,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 12.sp,
        color = TextLight
    ),
    labelSmall = TextStyle(  // Prompt Text
        fontFamily = FontFamily.Default,
        fontSize = 8.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 10.sp,
        color = TextLight
    )
)
