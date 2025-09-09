package net.arx.roommanagementapp.ui.room.mapper

import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity
import net.arx.roommanagementapp.framework.db.entity.TaskEntity
import net.arx.roommanagementapp.framework.db.entity.TaskTypeEntity
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.room.model.TaskUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import javax.inject.Inject

class RoomUiMapper @Inject constructor() {

    operator fun invoke(
        roomEntities: List<RoomEntity>,
        statuses: List<RoomStatusEntity>,
        users: List<UserUiItem> = emptyList(),
        isAdmin: Boolean,
        hasStatus: Boolean
    ): List<RoomUiItem> {

        return roomEntities.map { roomEntity ->
            val roomTask = statuses.firstOrNull { it.roomId == roomEntity.id }

            val user = users.firstOrNull { it.id == roomTask?.userId } ?: UserUiItem()

            val tasks = mapTasks(tasks = roomTask?.tasks)

            RoomUiItem(
                id = roomEntity.id,
                name = roomEntity.name,
                isAdmin = isAdmin,
                user = user,
                tasks = tasks,
                isLobby = hasStatus
            )
        }
    }

    operator fun invoke(
        roomEntity: RoomEntity,
        statusEntity: RoomStatusEntity?,
        isAdmin: Boolean
    ): RoomUiItem {

        val tasks = mapTasks(tasks = statusEntity?.tasks)
        val user = UserUiItem(id = statusEntity?.userId)

        return RoomUiItem(
            id = roomEntity.id,
            name = roomEntity.name,
            user = user,
            isAdmin = isAdmin,
            tasks = tasks
        )
    }

    private fun mapTasks(tasks: List<TaskEntity>?): List<TaskUiItem> {
        return tasks?.map {
            when(it.type) {
                TaskTypeEntity.MOPPING -> TaskUiItem.Mopping(
                    isDone = mutableStateOf(it.isDone)
                )

                TaskTypeEntity.SWEEPING -> TaskUiItem.Sweeping(
                    isDone = mutableStateOf(it.isDone)
                )

                TaskTypeEntity.GARBAGES -> TaskUiItem.Garbages(
                    isDone = mutableStateOf(it.isDone)
                )

                TaskTypeEntity.DISPOSABLES -> TaskUiItem.Disposables(
                    isDone = mutableStateOf(it.isDone)
                )

                TaskTypeEntity.BEDDINGS -> TaskUiItem.Beddings(
                    isDone = mutableStateOf(it.isDone)
                )
            }
        } ?: listOf(
            TaskUiItem.Mopping(),
            TaskUiItem.Sweeping(),
            TaskUiItem.Garbages(),
            TaskUiItem.Disposables(),
            TaskUiItem.Beddings()
        )
    }
}