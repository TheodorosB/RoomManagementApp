package net.arx.roommanagementapp.usecase.room

import net.arx.roommanagementapp.data.room.repository.RoomRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetAllRoomsUseCase @Inject constructor(
    private val roomRepositoryImpl: RoomRepositoryImpl
): UseCase {

    suspend operator fun invoke(): List<RoomEntity> {
        return try {
            roomRepositoryImpl.getAllRooms()
        } catch (ex: Exception) {
            Timber.tag(GetAllRoomsUseCase::class.simpleName.toString()).e(ex)
            emptyList()
        }
    }
}