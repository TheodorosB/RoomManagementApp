package net.arx.roommanagementapp.ui.dashboard.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class DashboardUiState(
    val navScreens: List<DashboardNavEntries> = listOf(
        DashboardNavEntries.Admin,
        DashboardNavEntries.Cleaner
    ),
    val backstackEntries: SnapshotStateList<DashboardNavEntries> = mutableStateListOf(
        DashboardNavEntries.Admin
    ),
    val onNavigateToAdminClicked: () -> Unit,
    val onNavigateToCleanerClicked: () -> Unit,
)

sealed class DashboardNavEntries(
) {
    object Admin : DashboardNavEntries()
    object Cleaner : DashboardNavEntries()

}

