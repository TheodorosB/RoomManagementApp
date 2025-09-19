package net.arx.roommanagementapp.ui.admin.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import net.arx.roommanagementapp.ui.admin.composable.AdminRoomScreen
import net.arx.roommanagementapp.ui.admin.viewmodel.AdminRoomViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.util.ext.navEntryScreen

internal fun DashboardNavEntries.adminRoomScreen(
    roomId: State<Long?>,
    date: State<DateUiItem>
): NavEntry<DashboardNavEntries> {
    return this.navEntryScreen {

        val viewModel: AdminRoomViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = roomId.value, key2 = date.value.dayStart.value) {
            viewModel.updateRoomDetails(roomId = roomId.value, date = date.value)
        }

        AdminRoomScreen(
            uiState = uiState
        )
    }
}