package net.arx.roommanagementapp.usecase.room

import net.arx.roommanagementapp.data.room.repository.RoomRepositoryImpl
import net.arx.roommanagementapp.framework.room.mapper.RoomEntityMapper
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class InsertRoomUseCase @Inject constructor(
    private val roomEntityMapper: RoomEntityMapper,
    private val roomRepositoryImpl: RoomRepositoryImpl
): UseCase {

    suspend operator fun invoke(roomName: String?) {
        if (roomName != null) {
            try {
                val roomEntity = roomEntityMapper(roomName = roomName)
                val roomId = roomRepositoryImpl.insertRoom(room = roomEntity)
                Timber.tag(InsertRoomUseCase::class.simpleName.toString())
                    .d("Room with id: $roomId inserted successfully")
            } catch (ex: Exception) {
                Timber.tag(InsertRoomUseCase::class.simpleName.toString()).e(ex)
            }
        } else {
            Timber.tag(InsertRoomUseCase::class.simpleName.toString())
                .w("Insert skipped: roomName was not provided")
        }
    }
}