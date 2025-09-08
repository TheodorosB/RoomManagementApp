package net.arx.roommanagementapp.framework.status.mapper

import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity
import net.arx.roommanagementapp.framework.db.entity.TaskEntity
import net.arx.roommanagementapp.framework.db.entity.TaskTypeEntity
import net.arx.roommanagementapp.ui.room.model.TaskUiItem
import javax.inject.Inject

class TaskEntityMapper @Inject constructor() {

    /*operator fun invoke(status: RoomStatusUiItem): RoomStatusEntity {
        return RoomStatusEntity(
            dayStart = status.dayStart,
            dayEnd = status.dayEnd,
            roomId = status.roomId,
            userId = status.userId,
            tasks = mapStatusType(status.tasks)
        )
    }*/

    fun mapStatusType(tasks: List<TaskUiItem>): List<TaskEntity> {
        return tasks.map { task ->
            when(task) {
                is TaskUiItem.Mopping -> TaskEntity(type = TaskTypeEntity.MOPPING, isDone = task.isDone.value)
                is TaskUiItem.Sweeping -> TaskEntity(type = TaskTypeEntity.SWEEPING, isDone = task.isDone.value)
                is TaskUiItem.Garbages -> TaskEntity(type = TaskTypeEntity.GARBAGES, isDone = task.isDone.value)
                is TaskUiItem.Disposables -> TaskEntity(type = TaskTypeEntity.DISPOSABLES, isDone = task.isDone.value)
                is TaskUiItem.Beddings -> TaskEntity(type = TaskTypeEntity.BEDDINGS, isDone = task.isDone.value)
            }
        }
    }
}