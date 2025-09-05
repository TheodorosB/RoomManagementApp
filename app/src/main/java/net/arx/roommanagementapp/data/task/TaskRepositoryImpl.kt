package net.arx.roommanagementapp.data.task

import net.arx.roommanagementapp.domain.task.TaskRepository
import net.arx.roommanagementapp.framework.db.entity.CleaningTask
import net.arx.roommanagementapp.framework.task.TaskDataSourceImpl
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSourceImpl
): TaskRepository {

    override suspend fun insertTask(task: CleaningTask): Long {
        return taskDataSource.insertTask(task = task)
    }

    override suspend fun getTasksByDate(dayStart: Long, dayEnd: Long): List<CleaningTask> {
        return taskDataSource.getTasksByDate(dayStart = dayStart, dayEnd = dayEnd)
    }

    override suspend fun getTasksByUser(dayStart: Long, dayEnd: Long, id: Long): List<CleaningTask> {
        return taskDataSource.getTasksByUser(dayStart = dayStart, dayEnd = dayEnd, id = id)
    }
}