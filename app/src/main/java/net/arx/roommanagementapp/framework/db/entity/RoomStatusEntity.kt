package net.arx.roommanagementapp.framework.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "room_statuses",
    foreignKeys = [
        ForeignKey(entity = RoomEntity::class, parentColumns = ["id"], childColumns = ["roomId"]),
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"])
    ],
    indices = [Index(value = ["roomId", "dayStart", "dayEnd"], unique = true)]
)
data class RoomStatusEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dayStart: Long,
    val dayEnd: Long,
    val roomId: Long,
    val userId: Long = 2,
    val tasks: List<TaskEntity>
)

data class TaskEntity(
    val type: TaskTypeEntity,
    val isDone: Boolean = true
)

enum class TaskTypeEntity {
    MOPPING,
    SWEEPING,
    GARBAGES,
    DISPOSABLES,
    BEDDINGS
}
