package net.arx.roommanagementapp.ui.base.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.ifable(condition: Boolean, then: Modifier.() -> Modifier): Modifier =
    if (condition) {
        then()
    } else {
        this
    }

fun Modifier.ifelseable(condition: Boolean, ifable: Modifier.() -> Modifier, elseable: Modifier.() -> Modifier): Modifier =
    if (condition) {
        then(ifable())
    } else {
        then(elseable())
    }

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
