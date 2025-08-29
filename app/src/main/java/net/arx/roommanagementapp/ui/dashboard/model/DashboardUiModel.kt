package net.arx.roommanagementapp.ui.dashboard.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.arx.roommanagementapp.util.ext.formatDate
import java.util.Calendar

data class DashboardUiState(
    val date: MutableState<String> = mutableStateOf(Calendar.getInstance().formatDate()),
    val navScreens: List<DashboardNavEntries> = listOf(
        DashboardNavEntries.Admin,
        DashboardNavEntries.Cleaner
    ),
    val backstackEntries: SnapshotStateList<DashboardNavEntries> = mutableStateListOf(
        DashboardNavEntries.Admin
    ),
    val onNavigateToAdminClicked: () -> Unit,
    val onNavigateToCleanerClicked: () -> Unit,
) {

    private val calendar = Calendar.getInstance()

    fun onPreviousDateClicked() {
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        date.value = calendar.formatDate()
    }

    fun onNextDateClicked() {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        date.value = calendar.formatDate()
    }
}

sealed class DashboardNavEntries(
) {
    object Admin : DashboardNavEntries()
    object Cleaner : DashboardNavEntries()

}

