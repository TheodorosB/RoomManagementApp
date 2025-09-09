package net.arx.roommanagementapp.domain.room

import net.arx.roommanagementapp.framework.db.entity.RoomEntity

interface RoomRepository {

    suspend fun insertRoom(room: RoomEntity): Long
    suspend fun roomExists(name: String): RoomEntity?
    suspend fun getRoom(id: Long): RoomEntity
    suspend fun getAllRooms(): List<RoomEntity>
}