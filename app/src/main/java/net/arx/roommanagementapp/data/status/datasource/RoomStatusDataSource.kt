package net.arx.roommanagementapp.data.status.datasource

import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity

interface RoomStatusDataSource {

    suspend fun insertRoomStatus(task: RoomStatusEntity): Long

    suspend fun getRoomStatuses(dayStart: Long, dayEnd: Long): List<RoomStatusEntity>

    suspend fun getRoomStatus(dayStart: Long, dayEnd: Long, id: Long): RoomStatusEntity?
}