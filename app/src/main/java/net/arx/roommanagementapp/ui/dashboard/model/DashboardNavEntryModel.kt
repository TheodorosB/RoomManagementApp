package net.arx.roommanagementapp.ui.dashboard.model


sealed class DashboardNavEntries(
    val hasBackButton: Boolean
) {
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