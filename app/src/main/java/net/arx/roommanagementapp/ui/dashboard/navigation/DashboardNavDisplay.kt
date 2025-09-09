package net.arx.roommanagementapp.ui.dashboard.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import net.arx.roommanagementapp.ui.admin.composable.LobbyScreen
import net.arx.roommanagementapp.ui.admin.composable.RoomManagementToolBar
import net.arx.roommanagementapp.ui.room.composable.RoomTasksScreen
import net.arx.roommanagementapp.ui.dashboard.composable.PinDialog
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.viewmodel.DashboardViewModel
import net.arx.roommanagementapp.ui.room.composable.RoomManagementScreen
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground

@Composable
fun DashboardNavDisplay(
    modifier: Modifier = Modifier
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ColorBaseBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top)
    ) {
        RoomManagementToolBar(
            date = uiState.value.date.value,
            hasBackButton = uiState.value.hasBackButton,
            onBackButtonClicked = uiState.value.onBackButtonClicked,
            onPreviousDateClick = { uiState.value.onPreviousDateClicked() },
            onNextDateClick = { uiState.value.onNextDateClicked() },
            onOpenAdminPinFormClicked = uiState.value.openAdminPinForm,
            onOpenUserPinFormClicked = uiState.value.openUserPinForm,
            onHomeScreenClicked = uiState.value.onNavigateToLobby
        )

        NavDisplay(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            backStack = uiState.value.backstackEntries,
            contentAlignment = Alignment.Center,
            entryProvider = { key ->
                when (key) {
                    DashboardNavEntries.Lobby -> NavEntry(
                        key = key,
                        content = {
                            LobbyScreen(
                                user = uiState.value.loggedInUser.value,
                                date = uiState.value.date.value,
                                onNavigateToRoom = uiState.value.onNavigateToRoom
                            )
                        }
                    )

                    DashboardNavEntries.RoomTasks -> NavEntry(
                        key = key,
                        content = {
                            RoomTasksScreen(
                                date = uiState.value.date.value,
                                roomId = uiState.value.selectedRoomId.value
                            )
                        }
                    )

                    DashboardNavEntries.RoomDetails -> NavEntry(
                        key = key,
                        content = {
                            RoomManagementScreen(
                                roomId = uiState.value.selectedRoomId.value,
                                date = uiState.value.date.value,
                            )
                        }
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