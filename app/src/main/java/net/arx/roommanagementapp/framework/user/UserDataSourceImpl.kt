package net.arx.roommanagementapp.framework.user

import net.arx.roommanagementapp.data.user.UserDataSource
import net.arx.roommanagementapp.framework.db.dao.UserDao
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userDao: UserDao
): UserDataSource {

    override suspend fun insertUser(userEntity: UserEntity): Long {
        return userDao.insertUser(userEntity = userEntity)
    }

    override suspend fun login(password: String): UserEntity? {
        return userDao.login(password = password)
    }

    override suspend fun getUser(id: Long): UserEntity? {
        return userDao.getUser(id = id)
    }

    override suspend fun getAllCleaners(): List<UserEntity> {
        return userDao.getAllCleaners()
    }
}