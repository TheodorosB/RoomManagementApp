package net.arx.roommanagementapp.ui.room.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.theme.ColorRoomCleanedStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomGeneralStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomRegularStatus
import net.arx.roommanagementapp.ui.user.model.UserUiItem

data class RoomUiItem(
    val id: Long = 0,
    val name: String = "",
    val isAdmin: Boolean = false,
    val isLobby: Boolean = false,
    val user: UserUiItem = UserUiItem(),
    @DrawableRes val roomIcon: Int = R.drawable.ic_room_cleaning_status,
    val statusIcon: ImageVector = Icons.Outlined.CheckCircle,
    val tasks: List<TaskUiItem> = listOf(
        TaskUiItem.Mopping(),
        TaskUiItem.Sweeping(),
        TaskUiItem.Garbages(),
        TaskUiItem.Disposables(),
        TaskUiItem.Beddings()
    )
) {
    private val isCleaned: Boolean
        get() = tasks.all { it.isDone.value }

    val isClickable: Boolean
        get() = isAdmin || !isCleaned

    val hasStatus: Boolean
        get() = isLobby && user.id != null
    val statusColor: Color
        get() = if(isCleaned) Color.Green else Color.Gray.copy(alpha = 0.2f)

    val roomIconColor: Color
        get() {
            val completedTasks = tasks.filter { it.isDone.value }
            return when(completedTasks.size) {
                0 -> ColorRoomGeneralStatus
                5 -> ColorRoomCleanedStatus
                else -> ColorRoomRegularStatus
            }
        }
}