package net.arx.roommanagementapp.ui.lobby.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.arx.roommanagementapp.ui.composable.dialog.FormDialog
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.lobby.model.LobbyUiState
import net.arx.roommanagementapp.ui.lobby.viewmodel.LobbyViewModel
import net.arx.roommanagementapp.ui.room.composable.RoomsRow
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.user.composable.UsersRow
import net.arx.roommanagementapp.ui.user.model.UserUiItem

@Composable
internal fun LobbyScreen(
    date: State<DateUiItem>,
    isAdmin: State<Boolean>,
    onNavigateToRoom: (Long) -> Unit
) {

    val viewModel: LobbyViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = date.value.dayStart.value, key2 = isAdmin.value) {
        viewModel.refreshData(date = date.value, isAdmin = isAdmin.value)
    }

    LobbyContent(
        uiState = uiState,
        onNavigateToRoom = onNavigateToRoom
    )
}

@Composable
private fun LobbyContent(
    uiState: State<LobbyUiState>,
    onNavigateToRoom: (Long) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.Top)
    ) {

        UsersRow(
            users = uiState.value.users,
            isAdmin = uiState.value.isAdmin.value,
            onAddNewUserClicked = uiState.value.onAddNewUserClicked
        )

        RoomsRow(
            rooms = uiState.value.rooms,
            isAdmin = uiState.value.isAdmin.value,
            onRoomClicked = onNavigateToRoom,
            onAddNewRoomClicked = uiState.value.onAddNewRoomClicked
        )
    }

    if(uiState.value.openDialogForm.value == true) {
        FormDialog(
            formUiItem = uiState.value.formUiItem.value,
            onDismissRequest = uiState.value.onCloseDialogForm,
            onValidateText = uiState.value.onValidateText,
            onAddCleanerClicked = uiState.value.onSubmitFormClicked
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
private fun LobbyContentPreview() {
    LobbyContent(
        uiState = remember { mutableStateOf(
            LobbyUiState(
                users = mutableStateListOf(
                    UserUiItem(name = "Γεωργία"),
                    UserUiItem(name = "Άννα"),
                    UserUiItem(name = "Ιωάννα"),
                    UserUiItem(name = "Δήμητρα"),
                    UserUiItem(name = "Ελένη")
                ),
                rooms = mutableStateListOf(
                    RoomUiItem(
                        id = 1,
                        name = "101",
                        user = UserUiItem(name = "Γεωργία"),
                        isAdmin = true,
                    ),
                    RoomUiItem(
                        id = 2,
                        name = "101",
                        user = UserUiItem(name = "Άννα"),
                        isAdmin = true,
                    ),
                    RoomUiItem(
                        id = 3,
                        name = "101",
                        user = UserUiItem(name = "Δήμητρα"),
                        isAdmin = true,
                    ),
                    RoomUiItem(
                        id = 4,
                        name = "101",
                        user = UserUiItem(name = "Ελένη"),
                        isAdmin = true,
                    )
                ),
                onSubmitFormClicked = {},
                onCloseDialogForm = {},
                onAddNewUserClicked = {},
                onAddNewRoomClicked = {},
                onValidateText = {}
            ))},
        onNavigateToRoom = {}
    )
}