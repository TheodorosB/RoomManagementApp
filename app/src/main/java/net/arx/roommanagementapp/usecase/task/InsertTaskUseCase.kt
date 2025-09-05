package net.arx.roommanagementapp.usecase.task

import net.arx.roommanagementapp.data.task.TaskRepositoryImpl
import net.arx.roommanagementapp.framework.task.mapper.TaskEntityMapper
import net.arx.roommanagementapp.ui.task.model.TaskUiItem
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepositoryImpl,
    private val taskEntityMapper: TaskEntityMapper
): UseCase {

    suspend operator fun invoke(task: TaskUiItem) {
        try {
            val taskEntity = taskEntityMapper(task = task)
            val taskId = taskRepository.insertTask(task = taskEntity)
            Timber.tag(InsertTaskUseCase::class.simpleName.toString())
                .d("Task with id: $taskId inserted successfully")
        } catch (ex: Exception) {
            Timber.tag(InsertTaskUseCase::class.simpleName.toString()).e(ex)
        }
    }
}