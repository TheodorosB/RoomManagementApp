package net.arx.roommanagementapp.ui.lobby.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.lobby.composable.LobbyScreen
import net.arx.roommanagementapp.ui.lobby.viewmodel.LobbyViewModel
import net.arx.roommanagementapp.ui.util.ext.navEntryScreen

internal fun DashboardNavEntries.lobbyScreen(
    date: State<DateUiItem>,
    isAdmin: State<Boolean>,
    onNavigateToRoom: (Long) -> Unit
): NavEntry<DashboardNavEntries> {
    return this.navEntryScreen {

        val viewModel: LobbyViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = date.value.dayStart.value, key2 = isAdmin.value) {
            viewModel.refreshData(date = date.value, isAdmin = isAdmin.value)
        }

        LobbyScreen(
            uiState = uiState,
            onNavigateToRoom = onNavigateToRoom
        )
    }
}