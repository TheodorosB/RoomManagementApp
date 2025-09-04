package net.arx.roommanagementapp.ui.cleaner.mapper

import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import javax.inject.Inject

class CleanerUiMapper @Inject constructor() {

    operator fun invoke(cleaners: List<UserEntity>): List<CleanerUiItem> {
        return cleaners.map {
            CleanerUiItem(name = it.username)
        }
    }

    operator fun invoke(user: UserUiItem): CleanerUiItem {
        return CleanerUiItem(
            name = user.name,
            isSelected = mutableStateOf(true),
            isClickable = false
        )
    }
}