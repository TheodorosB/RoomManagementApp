package net.arx.roommanagementapp.data.user

import net.arx.roommanagementapp.framework.db.entity.UserEntity

interface UserDataSource {

    suspend fun insertUser(userEntity: UserEntity): Long

    suspend fun usernameExists(username: String): UserEntity?

    suspend fun login(password: String): UserEntity?

    suspend fun getUser(id: Long): UserEntity?

    suspend fun getAllCleaners(): List<UserEntity>

}