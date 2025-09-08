package net.arx.roommanagementapp.domain.room

import net.arx.roommanagementapp.framework.db.entity.RoomEntity

interface RoomRepository {

    suspend fun insertRoom(room: RoomEntity): Long
    suspend fun getRoom(id: Long): RoomEntity
    suspend fun getAllRooms(): List<RoomEntity>
}