package net.arx.roommanagementapp.data.user

import net.arx.roommanagementapp.domain.user.UserRepository
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun insertUser(userEntity: UserEntity): Long {
        return userDataSource.insertUser(userEntity = userEntity)
    }

    override suspend fun login(password: String): UserEntity? {
        return userDataSource.login(password = password)
    }

    override suspend fun getUser(id: Long): UserEntity? {
        return userDataSource.getUser(id = id)
    }

    override suspend fun getAllCleaners(): List<UserEntity> {
        return userDataSource.getAllCleaners()
    }
}