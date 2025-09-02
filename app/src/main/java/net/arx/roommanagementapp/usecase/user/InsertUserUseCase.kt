package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.UserRepositoryImpl
import net.arx.roommanagementapp.framework.user.mapper.UserEntityMapper
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val userEntityMapper: UserEntityMapper
) : UseCase {

    suspend operator fun invoke(username: String?, password: String?) {
        if (username != null && password != null) {
            try {
                val userEntity = userEntityMapper(password = password, username = username)
                val userId = userRepository.insertUser(userEntity = userEntity)
                Timber.tag(InsertUserUseCase::class.simpleName.toString())
                    .d("User with id: $userId inserted successfully")
            } catch (ex: Exception) {
                Timber.tag(InsertUserUseCase::class.simpleName.toString()).e(ex)
            }
        } else {
            Timber.tag(InsertUserUseCase::class.simpleName.toString())
                .w("Insert skipped: username or password was null")
        }
    }
}