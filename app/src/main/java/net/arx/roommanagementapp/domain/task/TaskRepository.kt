package net.arx.roommanagementapp.domain.task

import net.arx.roommanagementapp.framework.db.entity.CleaningTask

interface TaskRepository {

    suspend fun insertTask(task: CleaningTask): Long

    suspend fun getTasksByDate(dayStart: Long, dayEnd: Long): List<CleaningTask>

    suspend fun getTasksByUser(dayStart: Long, dayEnd: Long, id: Long): List<CleaningTask>
}