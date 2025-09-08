package net.arx.roommanagementapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity

@Dao
interface RoomStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoomStatus(task: RoomStatusEntity): Long

    @Query("SELECT * FROM room_statuses WHERE dayStart = :dayStart AND dayEnd = :dayEnd ")
    suspend fun getRoomStatuses(dayStart: Long, dayEnd: Long): List<RoomStatusEntity>

    @Query("SELECT * FROM room_statuses WHERE dayStart = :dayStart AND dayEnd = :dayEnd AND roomId = :id")
    suspend fun getRoomStatus(dayStart: Long, dayEnd: Long, id: Long): RoomStatusEntity?
}
