package net.arx.roommanagementapp.ui.cleaner.mapper

import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import javax.inject.Inject

class CleanerUiMapper @Inject constructor() {

    operator fun invoke(cleaners: List<UserEntity>): List<CleanerUiItem> {
        return cleaners.map {
            CleanerUiItem(name = it.username)
        }
    }
}