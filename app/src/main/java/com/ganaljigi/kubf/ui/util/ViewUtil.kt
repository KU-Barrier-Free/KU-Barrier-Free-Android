package com.ganaljigi.kubf.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

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
    modifierIfFalse: Modifier = Modifier
): Modifier {
    return if (condition) {
        this.then(modifierIfTrue)
    } else {
        this.then(modifierIfFalse)
    }
}

fun Int.toDistanceString(): String {
    return when {
        this < 1000 -> "${this}m"
        else -> "%.1fkm".format(this / 1000.0)
    }
}