package net.arx.roommanagementapp.ui.dashboard.model

import androidx.navigation3.runtime.NavKey

sealed class DashboardNavEntries(
    val hasBackButton: Boolean
): NavKey {
    object Lobby : DashboardNavEntries(
        hasBackButton = false
    )
    object AdminRoom : DashboardNavEntries(
        hasBackButton = true
    )
    object UserRoom : DashboardNavEntries(
        hasBackButton = true
    )
}