package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun AdminScreen() {

    val viewModel: AdminViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AdminContent(
        uiState = uiState
    )
}

@Composable
fun AdminContent(
    uiState: State<AdminUiState>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top)
    ) {

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
        FormDialog(
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
                        isAdmin = true,
                    ),
                    RoomUiItem(
                        name = "101",
                        cleaner = CleanerUiItem(name = "Άννα"),
                        isAdmin = true,
                        status = mutableStateOf(RoomCleaningStatus.General()),
                    ),
                    RoomUiItem(
                        name = "101",
                        cleaner = CleanerUiItem(name = "Δήμητρα"),
                        isAdmin = true,
                        status = mutableStateOf(RoomCleaningStatus.Regular()),
                    ),
                    RoomUiItem(
                        name = "101",
                        cleaner = CleanerUiItem(name = "Ελένη"),
                        isAdmin = true,
                        status = mutableStateOf(RoomCleaningStatus.Cleaned()),
                    )
                ),
                onCleanerClicked = {},
                onSubmitFormClicked = {},
                onCloseAlertDialog = {},
                onAddNewCleanerClicked = {},
                onAddNewRoomClicked = {}
            ))}
    )
}