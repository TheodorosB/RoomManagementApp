package net.arx.roommanagementapp.ui.cleaner.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector

data class CleanerUiItem(
    val name: String,
    val icon: ImageVector = Icons.Outlined.AccountCircle
)
