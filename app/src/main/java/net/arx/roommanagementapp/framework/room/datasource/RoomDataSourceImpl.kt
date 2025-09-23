package net.arx.roommanagementapp.framework.room.datasource

import net.arx.roommanagementapp.data.room.datasource.RoomDataSource
import net.arx.roommanagementapp.framework.db.dao.RoomDao
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val roomDao: RoomDao
) : RoomDataSource {

    override suspend fun insertRoom(room: RoomEntity): Long {
        return roomDao.insertRoom(room = RoomEntity(name = room.name))
    }

    override suspend fun deleteRoom(id: Long) {
        roomDao.deleteRoom(id = id)
    }

    override suspend fun roomExists(name: String): RoomEntity? {
        return roomDao.roomExists(name = name)
    }

    override suspend fun getRoom(id: Long): RoomEntity {
        return roomDao.getRoom(id = id)
    }

    override suspend fun getAllRooms(): List<RoomEntity> {
        return roomDao.getAllRooms()
    }

}