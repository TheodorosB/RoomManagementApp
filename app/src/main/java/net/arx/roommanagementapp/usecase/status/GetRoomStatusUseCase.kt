package net.arx.roommanagementapp.usecase.status

import net.arx.roommanagementapp.data.status.repository.RoomStatusRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.RoomStatusEntity
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetRoomStatusUseCase @Inject constructor(
    private val repository: RoomStatusRepositoryImpl
): UseCase {

    suspend operator fun invoke(dayStart: Long, dayEnd: Long, id: Long): RoomStatusEntity? {
        return try {
            val roomStatus = repository.getRoomStatus(dayStart = dayStart, dayEnd = dayEnd, id = id)
            Timber.tag(GetRoomStatusUseCase::class.simpleName.toString())
                .d("Retrieved for room id = $id status = $roomStatus")
            roomStatus
        } catch (ex: Exception) {
            Timber.tag(GetRoomStatusUseCase::class.simpleName.toString()).e(ex)
            null
        }
    }
}