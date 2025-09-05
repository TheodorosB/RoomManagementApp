package net.arx.roommanagementapp.usecase.task

import net.arx.roommanagementapp.data.task.TaskRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.CleaningTask
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetTasksByDateUseCase @Inject constructor(
    private val taskRepository: TaskRepositoryImpl
): UseCase {

    suspend operator fun invoke(dayStart: Long, dayEnd: Long): List<CleaningTask> {
        return try {
            val tasks = taskRepository.getTasksByDate(dayStart = dayStart, dayEnd = dayEnd)
            Timber.tag(GetTasksByDateUseCase::class.simpleName.toString())
                .d("Retrieved tasks successfully $tasks")
            tasks
        } catch (ex: Exception) {
            Timber.tag(GetTasksByDateUseCase::class.simpleName.toString()).e(ex)
            emptyList()
        }
    }
}