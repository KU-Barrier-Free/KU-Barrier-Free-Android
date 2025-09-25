package com.ganaljigi.kubf.ui.util

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.ganaljigi.kubf.ui.theme.MainGreen

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit = {},
): Modifier =
    composed {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            onClick()
        }
    }

fun Modifier.conditionalModifier(
    condition: Boolean,
    modifierIfTrue: Modifier = Modifier,
    modifierIfFalse: Modifier = Modifier,
): Modifier {
    return if (condition) {
        this.then(modifierIfTrue)
    } else {
        this.then(modifierIfFalse)
    }
}

fun String.toDistanceString(): String {
    Log.d("ViewUtil", "toDistanceString: this = $this")
    val distanceInt = this.filter { it.isDigit() }.toIntOrNull()
    return when {
        distanceInt == null -> this
        distanceInt < 1000 -> "${distanceInt}m"
        else -> "%.1fkm".format(distanceInt / 1000.0)
    }
}

fun String.toAnnotatedString(matchKeyword: String): AnnotatedString {
    val firstIndex = this.indexOf(matchKeyword, ignoreCase = true)
    val lastIndex = this.lastIndexOf(matchKeyword, ignoreCase = true)
    if (this.isEmpty() || firstIndex == -1 || lastIndex == -1)
        return AnnotatedString(this)

    return buildAnnotatedString {
        append(this@toAnnotatedString.substring(0 until firstIndex))
        withStyle(style = SpanStyle(color = MainGreen)) {
            append(this@toAnnotatedString.substring(firstIndex until firstIndex + matchKeyword.length))
        }
        append(this@toAnnotatedString.substring(firstIndex + matchKeyword.length until this@toAnnotatedString.length))
    }
}