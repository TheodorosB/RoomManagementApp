package net.arx.roommanagementapp.data.room

import net.arx.roommanagementapp.domain.room.RoomRepository
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.framework.room.RoomDataSourceImpl
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSourceImpl
): RoomRepository {

    override suspend fun insertRoom(room: RoomEntity): Long {
        return roomDataSource.insertRoom(room = room)
    }

    override suspend fun getRoom(id: Long): RoomEntity {
        return roomDataSource.getRoom(id = id)
    }

    override suspend fun getAllRooms(): List<RoomEntity> {
        return roomDataSource.getAllRooms()
    }

}