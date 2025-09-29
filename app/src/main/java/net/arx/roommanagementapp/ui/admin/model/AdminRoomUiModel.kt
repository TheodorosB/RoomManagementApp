package net.arx.roommanagementapp.ui.admin.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.lobby.model.GridUiItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.theme.GridTasksRowBaseHeight
import net.arx.roommanagementapp.ui.theme.GridUsersRowBaseHeight
import net.arx.roommanagementapp.ui.user.model.UserUiItem

data class AdminRoomUiState(
    val date: MutableState<DateUiItem> = mutableStateOf(DateUiItem()),
    val users: SnapshotStateList<UserUiItem> = mutableStateListOf(),
    val room: MutableState<RoomUiItem> = mutableStateOf(RoomUiItem()),
    val onUserClicked: (Long?) -> Unit,
    val onUpdateStatus: () -> Unit
) {
    val userGridUiItem: GridUiItem
        get() = GridUiItem(
            columns = TOTAL_USERS_PER_ROW,
            roomsSize = users.size,
            hasExtraItem = false,
            baseHeight = GridUsersRowBaseHeight
        )

    val tasksGridUiItem: GridUiItem
        get() = GridUiItem(
            columns = TOTAL_TASKS_PER_ROW,
            roomsSize = room.value.tasks.size,
            hasExtraItem = false,
            baseHeight = GridTasksRowBaseHeight
        )

    companion object {
        const val TOTAL_USERS_PER_ROW = 4
        const val TOTAL_TASKS_PER_ROW = 4
    }
}
