package net.arx.roommanagementapp.framework.room

import net.arx.roommanagementapp.data.room.RoomDataSource
import net.arx.roommanagementapp.framework.db.dao.RoomDao
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val roomDao: RoomDao
) : RoomDataSource {

    override suspend fun insertRoom(room: RoomEntity): Long {
        return roomDao.insertRoom(room = RoomEntity(name = room.name))
    }

    override suspend fun getAllRooms(): List<RoomEntity> {
        return roomDao.getAllRooms()
    }

}