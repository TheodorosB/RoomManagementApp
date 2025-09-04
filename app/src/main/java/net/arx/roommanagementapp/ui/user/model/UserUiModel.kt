package net.arx.roommanagementapp.ui.user.model

import net.arx.roommanagementapp.framework.db.entity.UserRole

data class UserUiItem(
    val id: Long = 0,
    val name: String = "",
    val role: UserRole = UserRole.ADMIN,
) {

    val isAdmin: Boolean
        get() = role.name == UserRole.ADMIN.name
}
