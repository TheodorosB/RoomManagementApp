package net.arx.roommanagementapp.usecase.status

import net.arx.roommanagementapp.data.status.repository.RoomStatusRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetRoomStatusesUseCase @Inject constructor(
    private val repository: RoomStatusRepositoryImpl
): UseCase {

    suspend operator fun invoke(dayStart: Long, dayEnd: Long): List<RoomStatusEntity> {
        return try {
            val statuses = repository.getRoomStatuses(dayStart = dayStart, dayEnd = dayEnd)
            Timber.tag(GetRoomStatusesUseCase::class.simpleName.toString())
                .d("Retrieved statuses successfully $statuses")
            statuses
        } catch (ex: Exception) {
            Timber.tag(GetRoomStatusesUseCase::class.simpleName.toString()).e(ex)
            emptyList()
        }
    }
}