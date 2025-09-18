package net.arx.roommanagementapp.data.user.repository

import net.arx.roommanagementapp.data.user.datasource.UserDataSource
import net.arx.roommanagementapp.domain.user.repository.UserRepository
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
) : UserRepository {
    override suspend fun insertUser(userEntity: UserEntity): Long {
        return dataSource.insertUser(userEntity = userEntity)
    }

    override suspend fun usernameExists(username: String): UserEntity? {
        return dataSource.usernameExists(username = username)
    }

    override suspend fun login(password: String): UserEntity? {
        return dataSource.login(password = password)
    }

    override suspend fun getUser(id: Long): UserEntity? {
        return dataSource.getUser(id = id)
    }

    override suspend fun getAllCleaners(): List<UserEntity> {
        return dataSource.getAllCleaners()
    }
}