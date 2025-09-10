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
import net.arx.roommanagementapp.ui.admin.composable.AdminRoomScreen
import net.arx.roommanagementapp.ui.composable.dialog.PinDialog
import net.arx.roommanagementapp.ui.dashboard.composable.DashboardToolBar
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.viewmodel.DashboardViewModel
import net.arx.roommanagementapp.ui.lobby.composable.LobbyScreen
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground
import net.arx.roommanagementapp.ui.user.composable.UserRoomScreen

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
        DashboardToolBar(
            date = uiState.value.date.value,
            hasBackButton = uiState.value.hasBackButton,
            onBackButtonClicked = uiState.value.onBackButtonClicked,
            onPreviousDateClick = { uiState.value.onPreviousDateClicked() },
            onNextDateClick = { uiState.value.onNextDateClicked() },
            onOpenAdminPinFormClicked = uiState.value.openAdminPinForm
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
                                date = uiState.value.date,
                                isAdmin = uiState.value.isAdmin,
                                onNavigateToRoom = uiState.value.onNavigateToRoom
                            )
                        }
                    )

                    DashboardNavEntries.UserRoom -> NavEntry(
                        key = key,
                        content = {
                            UserRoomScreen(
                                date = uiState.value.date.value,
                                roomId = uiState.value.selectedRoomId.value
                            )
                        }
                    )

                    DashboardNavEntries.AdminRoom -> NavEntry(
                        key = key,
                        content = {
                            AdminRoomScreen(
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