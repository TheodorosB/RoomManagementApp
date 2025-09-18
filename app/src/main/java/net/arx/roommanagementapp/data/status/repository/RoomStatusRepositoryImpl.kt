package net.arx.roommanagementapp.data.status.repository

import net.arx.roommanagementapp.domain.status.repository.RoomStatusRepository
import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity
import net.arx.roommanagementapp.framework.status.datasource.RoomStatusDataSourceImpl
import javax.inject.Inject

class RoomStatusRepositoryImpl @Inject constructor(
    private val dataSource: RoomStatusDataSourceImpl
): RoomStatusRepository {

    override suspend fun insertRoomStatus(task: RoomStatusEntity): Long {
        return dataSource.insertRoomStatus(task = task)
    }
    override suspend fun getRoomStatuses(dayStart: Long, dayEnd: Long): List<RoomStatusEntity> {
        return dataSource.getRoomStatuses(dayStart = dayStart, dayEnd = dayEnd)
    }
    override suspend fun getRoomStatus(dayStart: Long, dayEnd: Long, id: Long): RoomStatusEntity? {
        return dataSource.getRoomStatus(dayStart = dayStart, dayEnd = dayEnd, id = id)
    }
}