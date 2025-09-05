package net.arx.roommanagementapp.framework.task

import net.arx.roommanagementapp.data.task.TaskDataSource
import net.arx.roommanagementapp.framework.db.dao.CleaningTaskDao
import net.arx.roommanagementapp.framework.db.entity.CleaningTask
import javax.inject.Inject

class TaskDataSourceImpl @Inject constructor(
    private val taskDao: CleaningTaskDao
): TaskDataSource {

    override suspend fun insertTask(task: CleaningTask): Long {
        return taskDao.insertTask(task = task)
    }

    override suspend fun getTasksByDate(dayStart: Long, dayEnd: Long): List<CleaningTask> {
        return taskDao.getTasksByDate(dayStart = dayStart, dayEnd = dayEnd)
    }

    override suspend fun getTasksByUser(dayStart: Long, dayEnd: Long, id: Long): List<CleaningTask> {
        return taskDao.getTasksByUser(dayStart = dayStart, dayEnd = dayEnd, id = id)
    }

}