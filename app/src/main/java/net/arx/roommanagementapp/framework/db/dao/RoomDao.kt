package net.arx.roommanagementapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.arx.roommanagementapp.framework.db.entity.RoomEntity

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomEntity): Long

    @Query("SELECT * FROM rooms")
    suspend fun getAllRooms(): List<RoomEntity>
}
