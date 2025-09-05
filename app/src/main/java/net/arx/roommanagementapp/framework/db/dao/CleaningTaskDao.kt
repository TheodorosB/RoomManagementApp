package net.arx.roommanagementapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.arx.roommanagementapp.framework.db.entity.CleaningTask

@Dao
interface CleaningTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: CleaningTask): Long

    @Query("SELECT * FROM cleaning_tasks WHERE dayStart = :dayStart AND dayEnd = :dayEnd ")
    suspend fun getTasksByDate(dayStart: Long, dayEnd: Long): List<CleaningTask>

    @Query("SELECT * FROM cleaning_tasks WHERE dayStart = :dayStart AND dayEnd = :dayEnd AND userId = :id")
    suspend fun getTasksByUser(dayStart: Long, dayEnd: Long, id: Long): List<CleaningTask>
}
