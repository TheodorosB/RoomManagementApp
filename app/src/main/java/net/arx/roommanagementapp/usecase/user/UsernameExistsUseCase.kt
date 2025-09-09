package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.UserRepositoryImpl
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class UsernameExistsUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : UseCase {

    suspend operator fun invoke(username: String): Boolean {
        return try {
            val user = userRepository.usernameExists(username = username)
            user != null
        } catch (ex: Exception) {
            Timber.tag(UsernameExistsUseCase::class.simpleName.toString()).e(ex)
            false
        }
    }
}