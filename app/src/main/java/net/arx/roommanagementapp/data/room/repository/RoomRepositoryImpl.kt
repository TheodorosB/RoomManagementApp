package net.arx.roommanagementapp.data.room.repository

import net.arx.roommanagementapp.domain.room.repository.RoomRepository
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.framework.room.datasource.RoomDataSourceImpl
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSourceImpl
): RoomRepository {

    override suspend fun insertRoom(room: RoomEntity): Long {
        return dataSource.insertRoom(room = room)
    }

    override suspend fun deleteRoom(id: Long) {
        dataSource.deleteRoom(id = id)
    }

    override suspend fun roomExists(name: String): RoomEntity? {
        return dataSource.roomExists(name = name)
    }

    override suspend fun getRoom(id: Long): RoomEntity {
        return dataSource.getRoom(id = id)
    }

    override suspend fun getAllRooms(): List<RoomEntity> {
        return dataSource.getAllRooms()
    }

}