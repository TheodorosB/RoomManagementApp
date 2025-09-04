package net.arx.roommanagementapp.ui.user.mapper

import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import javax.inject.Inject

class UserUiMapper @Inject constructor() {

    operator fun invoke(userEntity: UserEntity): UserUiItem {
        return UserUiItem(
            id = userEntity.id,
            name = userEntity.username,
            role = userEntity.role
        )
    }
}