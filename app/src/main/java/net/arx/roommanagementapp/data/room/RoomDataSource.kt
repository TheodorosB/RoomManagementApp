package net.arx.roommanagementapp.data.room

import net.arx.roommanagementapp.framework.db.entity.RoomEntity

interface RoomDataSource {

    suspend fun insertRoom(room: RoomEntity): Long

    suspend fun getRoom(id : Long): RoomEntity

    suspend fun getAllRooms(): List<RoomEntity>
}