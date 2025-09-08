package net.arx.roommanagementapp.framework.user.mapper

import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.framework.db.entity.UserRole
import javax.inject.Inject

class UserEntityMapper @Inject constructor(

){
    operator fun invoke(username: String, password: String): UserEntity {
        return UserEntity(
            username = username,
            password = password,
            role = UserRole.USER
        )

    }
}