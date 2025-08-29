package net.arx.roommanagementapp.ui.room.mapper

import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import javax.inject.Inject

class RoomUiMapper @Inject constructor() {

    operator fun invoke(
        name: String,
        cleaner: String = "",
        isAdmin: Boolean,
        status: RoomCleaningStatus = RoomCleaningStatus.Cleaned()
    ): RoomUiItem {
        return RoomUiItem(
            name = name,
            cleaner = CleanerUiItem(
                name = name
            ),
            isAdmin = isAdmin,
            status = mutableStateOf(status)
        )
    }
}