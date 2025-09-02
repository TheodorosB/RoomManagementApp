package net.arx.roommanagementapp.framework.room.mapper

import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import javax.inject.Inject

class RoomEntityMapper @Inject constructor() {

    operator fun invoke(roomName: String): RoomEntity {
        return RoomEntity(
            name = roomName
        )
    }
}