package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.UserRepositoryImpl
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class PasswordExistsUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : UseCase {

    suspend operator fun invoke(password: String): Boolean {
        return try {
            val user = userRepository.passwordExists(password = password)
            user != null
        } catch (ex: Exception) {
            Timber.tag(PasswordExistsUseCase::class.simpleName.toString()).e(ex)
            false
        }
    }
}