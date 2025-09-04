package net.arx.roommanagementapp.ui.cleaner.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground
import net.arx.roommanagementapp.ui.theme.ColorDarkBaseBackground

data class CleanerUiState(
    val cleaner: MutableState<CleanerUiItem> = mutableStateOf(CleanerUiItem()),
    val rooms: List<RoomUiItem> = ROOMS
) {
    companion object {
        val ROOMS = listOf(
            RoomUiItem(
                status = mutableStateOf(RoomCleaningStatus.Regular()),
                isAdmin = false,
                name = "101",
            ),
            RoomUiItem(
                status = mutableStateOf(RoomCleaningStatus.General()),
                isAdmin = false,
                name = "102",
            ),
            RoomUiItem(
                status = mutableStateOf(RoomCleaningStatus.Regular()),
                isAdmin = false,
                name = "103",
            ),
            RoomUiItem(
                status = mutableStateOf(RoomCleaningStatus.General()),
                isAdmin = false,
                name = "104",
            )
        )
    }
}

data class CleanerUiItem(
    val name: String = "",
    val isClickable: Boolean = true,
    val isSelected: MutableState<Boolean> = mutableStateOf(false),
    val icon: ImageVector = Icons.Outlined.AccountCircle
) {
    val backgroundColor: Color
        get() = if(isSelected.value) ColorDarkBaseBackground else ColorBaseBackground

}