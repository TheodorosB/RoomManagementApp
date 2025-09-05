package net.arx.roommanagementapp.ui.user.mapper

import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import javax.inject.Inject

class UserUiMapper @Inject constructor() {

    operator fun invoke(userEntity: UserEntity): UserUiItem {
        return UserUiItem(
            id = userEntity.id,
            name = userEntity.username,
            role = userEntity.role,
            isSelected = mutableStateOf(true),
            isClickable = false
        )
    }

    operator fun invoke(users: List<UserEntity>): List<UserUiItem> {
        return users.map { user ->
            invoke(userEntity = user)
        }
    }
}