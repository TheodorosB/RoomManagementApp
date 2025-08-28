package net.arx.roommanagementapp.ui.room.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.theme.ColorRoomCleanedStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomGeneralStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomRegularStatus

data class RoomUiItem(
    val name: String,
    val cleaner: CleanerUiItem,
    val statusIcon: ImageVector = Icons.Outlined.CheckCircle,
    val status: MutableState<RoomCleaningStatus> = mutableStateOf(RoomCleaningStatus.Cleaned())
) {
    val isCleaned: Boolean
        get() {
            return status.value.tasks
                .filter { it.isRequired }
                .all { it.isDone.value } && status.value !is RoomCleaningStatus.Cleaned
        }

    val statusColor: Color
        get() = if(isCleaned) Color.Green else Color.Gray
}

sealed class RoomCleaningStatus(
    @DrawableRes val icon: Int,
    val color: Color,
    val tasks: List<CleaningTask>
) {
    class General : RoomCleaningStatus(
        icon = R.drawable.ic_room_cleaning_status,
        color = ColorRoomGeneralStatus,
        tasks = CleaningTask.allTasks(requiredCount = 5)
    )

    class Regular : RoomCleaningStatus(
        icon = R.drawable.ic_room_cleaning_status,
        color = ColorRoomRegularStatus,
        tasks = CleaningTask.allTasks(requiredCount = 3)
    )

    class Cleaned : RoomCleaningStatus(
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