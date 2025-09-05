package net.arx.roommanagementapp.framework.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cleaning_tasks",
    foreignKeys = [
        ForeignKey(entity = RoomEntity::class, parentColumns = ["roomId"], childColumns = ["roomId"]),
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"])
    ],
    indices = [Index(value = ["roomId", "dayStart", "dayEnd"], unique = true)]
)
data class CleaningTask(
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val dayStart: Long,
    val dayEnd: Long,
    val roomId: Long,
    val userId: Long,
    val cleaningType: CleaningType
)

enum class CleaningType {
    GENERAL,
    REGULAR,
    CLEANED
}
