package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.repository.UserRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
): UseCase {

    suspend operator fun invoke(): List<UserEntity> {
        return try {
            val users = userRepository.getAllCleaners()
            Timber.tag(GetAllUsersUseCase::class.simpleName.toString()).d("Retrieved all users successfully: $users")
            users
        } catch (ex: Exception) {
            Timber.tag(GetAllUsersUseCase::class.simpleName.toString()).e(ex)
            emptyList()
        }
    }
}