package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.repository.UserRepositoryImpl
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : UseCase {

    suspend operator fun invoke(password: String): UserEntity? {
        return try {
            val user = userRepository.login(password = password)
            Timber.tag(InsertUserUseCase::class.simpleName.toString()).d("User with id: ${user?.id} inserted successfully")
            user
        } catch (ex: Exception) {
            Timber.tag(InsertUserUseCase::class.simpleName.toString()).e(ex)
            null
        }
    }
}