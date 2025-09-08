package net.arx.roommanagementapp.framework.status

import net.arx.roommanagementapp.data.task.RoomStatusDataSource
import net.arx.roommanagementapp.framework.db.dao.RoomStatusDao
import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity
import javax.inject.Inject

class RoomStatusDataSourceImpl @Inject constructor(
    private val roomStatusDao: RoomStatusDao
): RoomStatusDataSource {

    override suspend fun insertRoomStatus(task: RoomStatusEntity): Long {
        return roomStatusDao.insertRoomStatus(task = task)
    }
    override suspend fun getRoomStatuses(dayStart: Long, dayEnd: Long): List<RoomStatusEntity> {
        return roomStatusDao.getRoomStatuses(dayStart = dayStart, dayEnd = dayEnd)
    }

    override suspend fun getRoomStatus(dayStart: Long, dayEnd: Long, id: Long): RoomStatusEntity? {
        return roomStatusDao.getRoomStatus(dayStart = dayStart, dayEnd = dayEnd, id = id)
    }

}