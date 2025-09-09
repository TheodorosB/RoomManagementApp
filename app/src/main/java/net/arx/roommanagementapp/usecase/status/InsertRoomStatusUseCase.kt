package net.arx.roommanagementapp.usecase.status

import net.arx.roommanagementapp.data.task.RoomStatusRepositoryImpl
import net.arx.roommanagementapp.framework.status.mapper.TaskEntityMapper
import net.arx.roommanagementapp.ui.room.model.TaskUiItem
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class InsertRoomStatusUseCase @Inject constructor(
    private val taskRepository: RoomStatusRepositoryImpl,
    private val taskEntityMapper: TaskEntityMapper
): UseCase {

    suspend operator fun invoke(
        userId: Long?,
        roomId: Long,
        dayStart: Long,
        dayEnd: Long,
        tasks: List<TaskUiItem>
    ) {
        try {
            val roomStatusEntity = taskEntityMapper(dayStart = dayStart, dayEnd = dayEnd, roomId = roomId, userId = userId, tasks = tasks)
            val taskId = taskRepository.insertRoomStatus(task = roomStatusEntity)
            Timber.tag(InsertRoomStatusUseCase::class.simpleName.toString())
                .d("Task with id: $taskId inserted successfully")
        } catch (ex: Exception) {
            Timber.tag(InsertRoomStatusUseCase::class.simpleName.toString()).e(ex)
        }
    }
}