package com.ganaljigi.kubf.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@Immutable
data class KUBFTypography(
    // Bold
    val bold18: TextStyle, // 21

    // SemiBold
    val semiBold20: TextStyle, // 24
    val semiBold18: TextStyle, // 21
    val semiBold16 : TextStyle, // 19
    val semiBold14: TextStyle, // 16
    val semiBold13: TextStyle, // 16

    // Medium
    val medium20: TextStyle, // 24
    val medium15: TextStyle, // 18
    val medium14 : TextStyle, // 17
    val medium13: TextStyle, // 16

    // Regular
    val regular16: TextStyle, // 16
    val regular14: TextStyle, // 17
    val regular13: TextStyle, // 16
    val regular12: TextStyle, // 14

    // Extra
    val homeSpecial: TextStyle,
    val buildingInfoSpecial: TextStyle,
)