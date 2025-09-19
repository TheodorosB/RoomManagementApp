package net.arx.roommanagementapp.ui.dashboard.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.ui.NavDisplay
import net.arx.roommanagementapp.ui.admin.navigation.adminRoomScreen
import net.arx.roommanagementapp.ui.composable.dialog.PinDialog
import net.arx.roommanagementapp.ui.dashboard.composable.DashboardToolBar
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.viewmodel.DashboardViewModel
import net.arx.roommanagementapp.ui.lobby.navigation.lobbyScreen
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.user.navigation.userRoomScreen

@Composable
internal fun DashboardNavDisplay() {
    val viewModel: DashboardViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorBaseBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.Top)
    ) {
        DashboardToolBar(
            date = uiState.value.date.value,
            hasBackButton = uiState.value.hasBackButton,
            onBackButtonClicked = uiState.value.onBackButtonClicked,
            onPreviousDateClick = { uiState.value.onPreviousDateClicked() },
            onNextDateClick = { uiState.value.onNextDateClicked() },
            onOpenAdminPinFormClicked = uiState.value.openAdminPinForm
        )

        NavDisplay(
            modifier = Modifier.padding(horizontal = SpacingHalf_8dp),
            backStack = uiState.value.backstackEntries,
            contentAlignment = Alignment.Center,
            entryProvider = { key ->
                when (key) {
                    DashboardNavEntries.Lobby -> key.lobbyScreen(
                        date = uiState.value.date,
                        isAdmin = uiState.value.isAdmin,
                        onNavigateToRoom = uiState.value.onNavigateToRoom
                    )

                    DashboardNavEntries.AdminRoom -> key.adminRoomScreen(
                        roomId = uiState.value.selectedRoomId,
                        date = uiState.value.date,
                    )

                    DashboardNavEntries.UserRoom -> key.userRoomScreen(
                        date = uiState.value.date,
                        roomId = uiState.value.selectedRoomId
                    )
                }
            }
        )
    }

    if(uiState.value.openPinDialog.value) {
        PinDialog(
            pinForm = uiState.value.pinFormUiItem,
            onPasswordComplete = uiState.value.onPinComplete,
            onDismissRequest = uiState.value.onPinDialogDismiss
        )
    }
}