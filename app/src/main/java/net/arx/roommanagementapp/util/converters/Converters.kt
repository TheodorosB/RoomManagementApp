package net.arx.roommanagementapp.util.converters

import androidx.room.TypeConverter
import net.arx.roommanagementapp.framework.db.entity.UserRole

class Converters {

    @TypeConverter
    fun fromUserRole(role: UserRole): String {
        return role.name
    }

    @TypeConverter
    fun toUserRole(value: String): UserRole {
        return UserRole.valueOf(value)
    }
}