package net.arx.roommanagementapp.domain.user

import net.arx.roommanagementapp.framework.db.entity.UserEntity

interface UserRepository {

    suspend fun insertUser(userEntity: UserEntity): Long

    suspend fun login(username: String, password: String): UserEntity?

    suspend fun getAllCleaners(): List<UserEntity>
}