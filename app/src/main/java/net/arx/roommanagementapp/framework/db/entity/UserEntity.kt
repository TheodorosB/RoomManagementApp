package net.arx.roommanagementapp.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val password: String,
    val role: UserRole
)

enum class UserRole {
    ADMIN,
    CLEANER
}
