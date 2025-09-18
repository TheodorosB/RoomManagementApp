package net.arx.roommanagementapp.framework.util.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.arx.roommanagementapp.framework.db.entity.TaskEntity
import net.arx.roommanagementapp.framework.db.entity.TaskTypeEntity
import net.arx.roommanagementapp.framework.db.entity.UserRole

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromTaskList(tasks: List<TaskEntity>): String {
        return gson.toJson(tasks)
    }

    @TypeConverter
    fun toTaskList(data: String): List<TaskEntity> {
        val listType = object : TypeToken<List<TaskEntity>>() {}.type
        return gson.fromJson(data, listType)
    }
    @TypeConverter
    fun fromUserRole(role: UserRole): String {
        return role.name
    }

    @TypeConverter
    fun toUserRole(value: String): UserRole {
        return UserRole.valueOf(value)
    }

    @TypeConverter
    fun fromTaskType(value: TaskTypeEntity): String = value.name

    @TypeConverter
    fun toTaskType(value: String): TaskTypeEntity = TaskTypeEntity.valueOf(value)
}