package net.arx.roommanagementapp.ui.room.mapper

import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.framework.db.entity.CleaningTask
import net.arx.roommanagementapp.framework.db.entity.CleaningType
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import javax.inject.Inject

class RoomUiMapper @Inject constructor() {

    operator fun invoke(
        roomEntities: List<RoomEntity>,
        tasks: List<CleaningTask>,
        users: List<UserUiItem> = emptyList(),
        isAdmin: Boolean
    ): List<RoomUiItem> {

        return roomEntities.map { roomEntity ->
            val roomTask = tasks.firstOrNull { it.roomId == roomEntity.roomId }

            val roomStatusType = when(roomTask?.cleaningType) {
                CleaningType.GENERAL -> RoomCleaningStatus.General()
                CleaningType.REGULAR -> RoomCleaningStatus.Regular()
                else -> RoomCleaningStatus.Cleaned()
            }

            val user = users.firstOrNull { it.id == roomTask?.userId } ?: UserUiItem()

            RoomUiItem(
                id = roomEntity.roomId,
                name = roomEntity.name,
                isAdmin = isAdmin,
                user = user,
                status = mutableStateOf(roomStatusType)
            )
        }
    }

    fun mapRoomsByTasks(
        roomEntities: List<RoomEntity>,
        tasks: List<CleaningTask>,
        isAdmin: Boolean
    ): List<RoomUiItem> {
        return tasks.map { task ->
            val room = roomEntities.firstOrNull { it.roomId == task.roomId }

            val roomStatusType = when(task.cleaningType) {
                CleaningType.GENERAL -> RoomCleaningStatus.General()
                CleaningType.REGULAR -> RoomCleaningStatus.Regular()
                else -> RoomCleaningStatus.Cleaned()
            }

            RoomUiItem(
                id = room?.roomId ?: 0,
                name = room?.name ?: "",
                isAdmin = isAdmin,
                status = mutableStateOf(roomStatusType)
            )
        }
    }
}