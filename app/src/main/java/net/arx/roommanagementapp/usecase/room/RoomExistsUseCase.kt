package net.arx.roommanagementapp.usecase.room

import net.arx.roommanagementapp.data.room.RoomRepositoryImpl
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class RoomExistsUseCase @Inject constructor(
    private val repository: RoomRepositoryImpl
): UseCase {

    suspend operator fun invoke(name: String): Boolean {
        return try {
            val room = repository.roomExists(name = name)
            return room != null
        } catch (ex: Exception) {
            Timber.tag(InsertRoomUseCase::class.simpleName.toString()).e(ex)
            false
        }
    }
}