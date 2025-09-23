package net.arx.roommanagementapp.usecase.user

import net.arx.roommanagementapp.data.user.repository.UserRepositoryImpl
import net.arx.roommanagementapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
): UseCase {

    suspend operator fun invoke(id : Long){
        try {
            userRepository.deleteUser(id = id)
        } catch (ex: Exception) {
            Timber.tag(DeleteUserUseCase::class.simpleName.toString()).e(ex)
        }
    }
}