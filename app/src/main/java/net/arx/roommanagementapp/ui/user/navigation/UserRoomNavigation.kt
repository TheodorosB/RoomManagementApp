package net.arx.roommanagementapp.ui.user.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.user.composable.UserRoomScreen
import net.arx.roommanagementapp.ui.user.viewmodel.UserRoomViewModel
import net.arx.roommanagementapp.ui.util.ext.navEntryScreen

internal fun DashboardNavEntries.userRoomScreen(
    roomId: State<Long?>,
    date: State<DateUiItem>
): NavEntry<DashboardNavEntries> {
    return this.navEntryScreen {

        val viewModel: UserRoomViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = roomId, key2 = date.value.dayStart.value) {
            viewModel.loadUserData(date = date.value, roomId = roomId.value)
        }

        UserRoomScreen(
            uiState = uiState
        )
    }
}