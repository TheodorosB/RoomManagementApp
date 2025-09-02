package net.arx.roommanagementapp.ui.room.mapper

import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import javax.inject.Inject

class RoomUiMapper @Inject constructor() {

    operator fun invoke(
        name: String,
        isAdmin: Boolean,
        cleaner: String = "",
        status: RoomCleaningStatus = RoomCleaningStatus.Cleaned()
    ): RoomUiItem {
        return RoomUiItem(
            name = name,
            cleaner = CleanerUiItem(
                name = name
            ),
            isAdmin = isAdmin,
            status = mutableStateOf(status)
        )
    }

    operator fun invoke(roomEntities: List<RoomEntity>, isAdmin: Boolean): List<RoomUiItem> {
        return roomEntities.map { roomEntity ->
            RoomUiItem(
                name = roomEntity.name,
                isAdmin = isAdmin
            )
        }
    }
}