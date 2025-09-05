package net.arx.roommanagementapp.framework.task.mapper

import net.arx.roommanagementapp.framework.db.entity.CleaningTask
import net.arx.roommanagementapp.ui.task.model.TaskUiItem
import javax.inject.Inject

class TaskEntityMapper @Inject constructor() {

    operator fun invoke(task: TaskUiItem): CleaningTask {
        return CleaningTask(
            dayStart = task.dayStart,
            dayEnd = task.dayEnd,
            roomId = task.roomId,
            userId = task.userId,
            cleaningType = task.cleaningType
        )
    }
}