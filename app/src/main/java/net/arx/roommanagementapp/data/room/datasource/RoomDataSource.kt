package net.arx.roommanagementapp.data.room.datasource

import net.arx.roommanagementapp.framework.db.entity.RoomEntity

interface RoomDataSource {

    suspend fun insertRoom(room: RoomEntity): Long
    suspend fun roomExists(name: String): RoomEntity?

    suspend fun getRoom(id : Long): RoomEntity

    suspend fun getAllRooms(): List<RoomEntity>
}