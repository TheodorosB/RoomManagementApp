package net.arx.roommanagementapp.ui.admin.composable

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.admin.viewmodel.AdminViewModel
import net.arx.roommanagementapp.ui.user.composable.UsersRow
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.composable.RoomsRow
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem

@Composable
fun AdminScreen(
    date: DateUiItem
) {

    val viewModel: AdminViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = date.dayStart.value, key2 = date.dayEnd.value) {
        viewModel.updateDate(date = date)
    }

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

        UsersRow(
            users = uiState.value.users,
            onUserClicked = uiState.value.onUserClicked,
            onAddNewUserClicked = uiState.value.onAddNewUserClicked
        )

        RoomsRow(
            rooms = uiState.value.rooms,
            onRoomClicked = uiState.value.onRoomClicked,
            onAddNewRoomClicked = uiState.value.onAddNewRoomClicked
        )
    }

    if(uiState.value.openDialogForm.value == true) {
        FormDialog(
            formUiItem = uiState.value.formUiItem.value,
            onDismissRequest = uiState.value.onCloseDialogForm,
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
                        status = mutableStateOf(RoomCleaningStatus.General()),
                    ),
                    RoomUiItem(
                        id = 3,
                        name = "101",
                        user = UserUiItem(name = "Δήμητρα"),
                        isAdmin = true,
                        status = mutableStateOf(RoomCleaningStatus.Regular()),
                    ),
                    RoomUiItem(
                        id = 4,
                        name = "101",
                        user = UserUiItem(name = "Ελένη"),
                        isAdmin = true,
                        status = mutableStateOf(RoomCleaningStatus.Cleaned()),
                    )
                ),
                onUserClicked = {},
                onSubmitFormClicked = {},
                onCloseDialogForm = {},
                onAddNewUserClicked = {},
                onAddNewRoomClicked = {},
                onRoomClicked = {}
            ))}
    )
}