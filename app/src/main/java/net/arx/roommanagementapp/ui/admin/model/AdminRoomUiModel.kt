package net.arx.roommanagementapp.ui.admin.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem

data class AdminRoomUiState(
    val date: MutableState<DateUiItem> = mutableStateOf(DateUiItem()),
    val users: SnapshotStateList<UserUiItem> = mutableStateListOf(),
    val room: MutableState<RoomUiItem> = mutableStateOf(RoomUiItem()),
    val onUserClicked: (Long?) -> Unit,
    val onUpdateStatus: () -> Unit
)
