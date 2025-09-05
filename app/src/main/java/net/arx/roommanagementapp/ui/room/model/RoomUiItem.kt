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
    val statusIcon: ImageVector = Icons.Outlined.CheckCircle,
    val status: MutableState<RoomCleaningStatus> = mutableStateOf(RoomCleaningStatus.Cleaned())
) {
    private val isCleaned: Boolean
        get() {
            return status.value.tasks
                .filter { it.isRequired }
                .all { it.isDone.value } && status.value !is RoomCleaningStatus.Cleaned
        }

    val statusIsVisible: Boolean
        get() = status.value !is RoomCleaningStatus.Cleaned && isAdmin

    val statusColor: Color
        get() = if(isCleaned) Color.Green else Color.Gray.copy(alpha = 0.2f)
}

sealed class RoomCleaningStatus(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val color: Color,
    val tasks: List<CleaningTask>
) {
    class General : RoomCleaningStatus(
        title = R.string.room_general_status,
        icon = R.drawable.ic_room_cleaning_status,
        color = ColorRoomGeneralStatus,
        tasks = CleaningTask.allTasks(requiredCount = 5)
    )

    class Regular : RoomCleaningStatus(
        title = R.string.room_regular_status,
        icon = R.drawable.ic_room_cleaning_status,
        color = ColorRoomRegularStatus,
        tasks = CleaningTask.allTasks(requiredCount = 3)
    )

    class Cleaned : RoomCleaningStatus(
        title = R.string.room_clean_status,
        icon = R.drawable.ic_room_cleaning_status,
        color = ColorRoomCleanedStatus,
        tasks = CleaningTask.allTasks(requiredCount = 5, allDone = true)
    )
}

data class CleaningTask(
    @StringRes val name: Int,
    val isDone: MutableState<Boolean> = mutableStateOf(false),
    val isRequired: Boolean = true
) {

    val textDecoration: TextDecoration
        get() = if(isDone.value) TextDecoration.LineThrough else TextDecoration.None

    fun onTaskClicked() {
        isDone.value = !isDone.value
    }
    companion object {
        fun allTasks(requiredCount: Int, allDone: Boolean = false): List<CleaningTask> {
            val baseTasks = listOf(
                CleaningTask(R.string.cleaning_tasks_sweeping_title),
                CleaningTask(R.string.cleaning_tasks_garbages_title),
                CleaningTask(R.string.cleaning_tasks_mopping_title),
                CleaningTask(R.string.cleaning_tasks_disposables_title),
                CleaningTask(R.string.cleaning_tasks_bedding_title),
            )

            return baseTasks.mapIndexed { index, task ->
                task.copy(
                    isRequired = index < requiredCount,
                    isDone = mutableStateOf(allDone && index < requiredCount)
                )
            }
        }
    }
}