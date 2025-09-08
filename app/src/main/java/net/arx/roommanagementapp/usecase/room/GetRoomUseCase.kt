package net.arx.roommanagementapp.usecase.room

import net.arx.roommanagementapp.data.room.RoomRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetRoomUseCase @Inject constructor(
    private val roomRepositoryImpl: RoomRepositoryImpl
): UseCase {

    suspend operator fun invoke(id: Long): RoomEntity {
        return try {
            roomRepositoryImpl.getRoom(id = id)
        } catch (ex: Exception) {
            Timber.tag(GetRoomUseCase::class.simpleName.toString()).e(ex)
            RoomEntity()
        }
    }
}