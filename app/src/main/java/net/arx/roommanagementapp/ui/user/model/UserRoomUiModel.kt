package net.arx.roommanagementapp.ui.user.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.framework.db.entity.UserRole
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem

data class UserRoomUiState(
    val date: MutableState<DateUiItem> = mutableStateOf(DateUiItem()),
    val user: MutableState<UserUiItem> = mutableStateOf(UserUiItem()),
    val room: MutableState<RoomUiItem> = mutableStateOf(RoomUiItem()),
    val onUpdateStatus: () -> Unit
)

data class UserUiItem(
    val id: Long? = null,
    val name: String = "",
    val isClickable: Boolean = true,
    val isSelected: MutableState<Boolean> = mutableStateOf(false),
    val role: UserRole = UserRole.USER,
) {

    val isAdmin: Boolean
        get() = role.name == UserRole.ADMIN.name
}
