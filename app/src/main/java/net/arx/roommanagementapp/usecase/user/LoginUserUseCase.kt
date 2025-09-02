package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.UserRepositoryImpl
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : UseCase {

    suspend operator fun invoke(username: String, password: String) {
        try {
            val userId = userRepository.login(username = username, password = password)
            Timber.tag(InsertUserUseCase::class.simpleName.toString()).d("User with id: $userId inserted successfully")
        } catch (ex: Exception) {
            Timber.tag(InsertUserUseCase::class.simpleName.toString()).e(ex)
        }
    }
}