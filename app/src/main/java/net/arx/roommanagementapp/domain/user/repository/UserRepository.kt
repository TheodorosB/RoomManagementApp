package net.arx.roommanagementapp.domain.user.repository

import net.arx.roommanagementapp.framework.db.entity.UserEntity

interface UserRepository {

    suspend fun insertUser(userEntity: UserEntity): Long

    suspend fun deleteUser(id: Long)

    suspend fun usernameExists(username: String): UserEntity?

    suspend fun login(password: String): UserEntity?

    suspend fun getUser(id: Long): UserEntity?

    suspend fun getAllCleaners(): List<UserEntity>
}