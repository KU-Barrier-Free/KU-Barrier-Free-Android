package com.ganaljigi.kubf.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ganalijigi.kubf.R

private val PretendardBold = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
    fontWeight = FontWeight.Bold,
)
private val PretendardSemiBold = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
    fontWeight = FontWeight.SemiBold,
)
private val PretendardMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
    fontWeight = FontWeight.Medium,
)
private val PretendardRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
    fontWeight = FontWeight.Normal,
)


internal val Typography = KUBFTypography(
    bold18 = PretendardBold.copy(
        fontSize = 18.sp,
        lineHeight = 21.sp,
    ),
    semiBold20 = PretendardSemiBold.copy(
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    semiBold18 = PretendardSemiBold.copy(
        fontSize = 18.sp,
        lineHeight = 21.sp,
    ),
    semiBold16 = PretendardSemiBold.copy(
        fontSize = 16.sp,
        lineHeight = 19.sp,
    ),
    semiBold14 = PretendardSemiBold.copy(
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),
    semiBold13 = PretendardSemiBold.copy(
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
    medium20 = PretendardMedium.copy(
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    medium15 = PretendardMedium.copy(
        fontSize = 15.sp,
        lineHeight = 18.sp,
    ),
    medium14 = PretendardMedium.copy(
        fontSize = 14.sp,
        lineHeight = 17.sp,
    ),
    medium13 = PretendardMedium.copy(
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
    regular16 = PretendardRegular.copy(
        fontSize = 16.sp,
        lineHeight = 16.sp,
    ),
    regular14 = PretendardRegular.copy(
        fontSize = 14.sp,
        lineHeight = 17.sp,
    ),
    regular13 = PretendardRegular.copy(
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
    regular12 = PretendardRegular.copy(
        fontSize = 12.sp,
        lineHeight = 14.sp,
    ),
    homeSpecial = PretendardMedium.copy(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = (-2.5).sp,
    ),
    buildingInfoSpecial = PretendardMedium.copy(
        fontSize = 14.sp,
        lineHeight = 25.sp,
        letterSpacing = (-2.5).sp,
    )

)

@Immutable
data class KUBFTypography(
    // Bold
    val bold18: TextStyle, // 21

    // SemiBold
    val semiBold20: TextStyle, // 24
    val semiBold18: TextStyle, // 21
    val semiBold16: TextStyle, // 19
    val semiBold14: TextStyle, // 16
    val semiBold13: TextStyle, // 16

    // Medium
    val medium20: TextStyle, // 24
    val medium15: TextStyle, // 18
    val medium14: TextStyle, // 17
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

val LocalTypography = staticCompositionLocalOf {
    Typography
}
