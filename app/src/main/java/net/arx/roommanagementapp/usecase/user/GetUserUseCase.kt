package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.repository.UserRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
): UseCase {

    suspend operator fun invoke(id: Long): UserEntity? {
        return try {
            val user = userRepository.getUser(id)
            Timber.tag(GetUserUseCase::class.simpleName.toString()).d("Retrieved user successfully: $user")
            user
        } catch (ex: Exception) {
            Timber.tag(GetUserUseCase::class.simpleName.toString()).e(ex)
            null
        }
    }
}