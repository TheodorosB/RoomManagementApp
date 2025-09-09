package net.arx.roommanagementapp.ui.user.mapper

import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.framework.db.entity.UserRole
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import javax.inject.Inject

class UserUiMapper @Inject constructor() {

    operator fun invoke(
        users: List<UserEntity>,
        isClickable: Boolean = false
    ): List<UserUiItem> {

        return users.map { user ->
            invoke(
                userEntity = user,
                isClickable = isClickable
            )
        }
    }

    operator fun invoke(
        userEntity: UserEntity?,
        isClickable: Boolean = false
    ): UserUiItem {

        return UserUiItem(
            id = userEntity?.id ?: 0,
            name = userEntity?.username ?: "",
            role = userEntity?.role ?: UserRole.USER,
            isSelected = mutableStateOf(false),
            isClickable = isClickable
        )
    }
}