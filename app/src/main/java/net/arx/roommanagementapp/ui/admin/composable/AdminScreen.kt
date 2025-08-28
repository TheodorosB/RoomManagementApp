package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.admin.viewmodel.AdminViewModel
import net.arx.roommanagementapp.ui.cleaner.composable.CleanersRow
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.room.composable.RoomsRow
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.room.model.RoomUiItem

@Composable
fun AdminScreen(
    navigateToCleaner: () -> Unit,
) {

    val viewModel: AdminViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AdminContent(
        uiState = uiState,
        navigateToCleaner = navigateToCleaner
    )
}

@Composable
fun AdminContent(
    uiState: State<AdminUiState>,
    navigateToCleaner: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top)
    ) {
        RoomManagementToolBar(
            date = uiState.value.date.value,
            onPreviousDateClick = { uiState.value.onPreviousDateClicked() },
            onNextDateClick = { uiState.value.onNextDateClicked() },
            onPinPanelIconClicked = uiState.value.onPinPanelIconClicked,
            onUserIconClicked = navigateToCleaner,
            onHomeScreenClicked = uiState.value.onHomeScreenClicked,
        )

        CleanersRow(
            cleaners = uiState.value.cleaners,
            onCleanerClicked = uiState.value.onCleanerClicked,
            onAddNewCleanerClicked = uiState.value.onAddNewCleanerClicked
        )

        RoomsRow(
            rooms = uiState.value.rooms,
            onAddNewRoomClicked = uiState.value.onAddNewRoomClicked
        )
    }

    if(uiState.value.openDialogForm.value == true) {
        CleanerDialogForm(
            formUiItem = uiState.value.formUiItem.value,
            onDismissRequest = uiState.value.onCloseAlertDialog,
            onAddCleanerClicked = uiState.value.onSubmitFormClicked
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
private fun AdminContentPreview() {
    AdminContent(
        uiState = remember { mutableStateOf(
            AdminUiState(
                cleaners = mutableStateListOf(
                    CleanerUiItem(name = "Γεωργία"),
                    CleanerUiItem(name = "Άννα"),
                    CleanerUiItem(name = "Ιωάννα"),
                    CleanerUiItem(name = "Δήμητρα"),
                    CleanerUiItem(name = "Ελένη")
                ),
                rooms = mutableStateListOf(
                    RoomUiItem(
                        name = "101",
                        cleaner = CleanerUiItem(name = "Γεωργία"),
                    ),
                    RoomUiItem(
                        name = "101",
                        cleaner = CleanerUiItem(name = "Άννα"),
                        status = mutableStateOf(RoomCleaningStatus.General()),
                    ),
                    RoomUiItem(
                        name = "101",
                        cleaner = CleanerUiItem(name = "Δήμητρα"),
                        status = mutableStateOf(RoomCleaningStatus.Regular()),
                    ),
                    RoomUiItem(
                        name = "101",
                        cleaner = CleanerUiItem(name = "Ελένη"),
                        status = mutableStateOf(RoomCleaningStatus.Cleaned()),
                    )
                ),
                onPinPanelIconClicked = {},
                onHomeScreenClicked = {},
                onCleanerClicked = {},
                onSubmitFormClicked = {},
                onCloseAlertDialog = {},
                onAddNewCleanerClicked = {},
                onAddNewRoomClicked = {}
            ))},
        navigateToCleaner = {}
    )
}