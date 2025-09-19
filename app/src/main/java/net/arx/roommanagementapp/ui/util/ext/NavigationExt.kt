package net.arx.roommanagementapp.ui.util.ext

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

internal fun <T : NavKey> T.navEntryScreen(
    metadata: Map<String, Any> = emptyMap(),
    content: @Composable (T) -> Unit,
): NavEntry<T> {
    return NavEntry(
        key = this,
        metadata = metadata,
        content = content
    )
}