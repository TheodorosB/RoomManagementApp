package net.arx.roommanagementapp.usecase.room

import net.arx.roommanagementapp.data.room.repository.RoomRepositoryImpl
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class DeleteRoomUseCase @Inject constructor(
    private val roomRepositoryImpl: RoomRepositoryImpl
): UseCase {

    suspend operator fun invoke(id: Long) {
        try {
            roomRepositoryImpl.deleteRoom(id = id)
        } catch (ex: Exception) {
            Timber.tag(DeleteRoomUseCase::class.simpleName.toString()).e(ex)
        }
    }
}