package net.arx.roommanagementapp.ui.room.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.theme.ColorRoomCleanedStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomGeneralStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomRegularStatus
import net.arx.roommanagementapp.ui.user.model.UserUiItem

data class RoomUiItem(
    val id: Long = 0,
    val name: String = "",
    val isAdmin: Boolean = false,
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

    val statusIsVisible: Boolean
        get() = isCleaned && isAdmin

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

sealed class TaskUiItem(
    @StringRes val title: Int,
    val color: Color,
    val isDone: MutableState<Boolean>
) {
    val textDecoration: TextDecoration
        get() = if (isDone.value) TextDecoration.LineThrough else TextDecoration.None

    fun onTaskClicked() {
        isDone.value = !isDone.value
    }

    class Mopping(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_mopping_title,
        color = ColorRoomGeneralStatus,
        isDone = isDone
    )

    class Sweeping(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_sweeping_title,
        color = ColorRoomRegularStatus,
        isDone = isDone
    )

    class Garbages(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_garbages_title,
        color = ColorRoomCleanedStatus,
        isDone = isDone
    )

    class Disposables(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_disposables_title,
        color = ColorRoomCleanedStatus,
        isDone = isDone
    )

    class Beddings(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_beddings_title,
        color = ColorRoomCleanedStatus,
        isDone = isDone
    )
}