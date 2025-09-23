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

    @Query("DELETE FROM rooms WHERE id = :id")
    suspend fun deleteRoom(id: Long)

    @Query("SELECT * FROM rooms WHERE name = :name")
    suspend fun roomExists(name: String): RoomEntity?

    @Query("SELECT * FROM rooms WHERE id = :id")
    suspend fun getRoom(id: Long): RoomEntity

    @Query("SELECT * FROM rooms")
    suspend fun getAllRooms(): List<RoomEntity>
}
