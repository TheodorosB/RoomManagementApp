package net.arx.roommanagementapp.ui.task.mapper

import net.arx.roommanagementapp.framework.db.entity.CleaningTask
import net.arx.roommanagementapp.framework.db.entity.CleaningType
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.task.model.TaskUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import javax.inject.Inject

class TaskUiMapper @Inject constructor() {

    operator fun invoke(task: CleaningTask): TaskUiItem {
        return TaskUiItem(
            id = task.taskId,
            dayStart = task.dayStart,
            dayEnd = task.dayEnd,
            roomId = task.roomId,
            userId = task.userId,
            cleaningType = task.cleaningType
        )
    }

    operator fun invoke(
        date: DateUiItem,
        roomId: Long,
        user: UserUiItem,
        status: RoomCleaningStatus
    ): TaskUiItem {

        val cleaningType = when(status) {
            is RoomCleaningStatus.General -> CleaningType.GENERAL
            is RoomCleaningStatus.Regular -> CleaningType.REGULAR
            is RoomCleaningStatus.Cleaned -> CleaningType.CLEANED
        }

        return TaskUiItem(
            dayStart = date.dayStart.value,
            dayEnd = date.dayEnd.value,
            roomId = roomId,
            userId = user.id ?: 0,
            cleaningType = cleaningType
        )
    }
}