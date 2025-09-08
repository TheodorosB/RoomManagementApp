package net.arx.roommanagementapp.ui.user.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import net.arx.roommanagementapp.framework.db.entity.UserRole
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground
import net.arx.roommanagementapp.ui.theme.ColorDarkBaseBackground

data class RoomTasksUiState(
    val user: MutableState<UserUiItem> = mutableStateOf(UserUiItem()),
    val room: MutableState<RoomUiItem> = mutableStateOf(RoomUiItem())
)

data class UserUiItem(
    val id: Long? = null,
    val name: String = "",
    val isClickable: Boolean = true,
    val isSelected: MutableState<Boolean> = mutableStateOf(false),
    val icon: ImageVector = Icons.Outlined.AccountCircle,
    val role: UserRole = UserRole.USER,
) {
    val backgroundColor: Color
        get() = if(isSelected.value) ColorDarkBaseBackground else ColorBaseBackground
    val isAdmin: Boolean
        get() = role.name == UserRole.ADMIN.name
}
