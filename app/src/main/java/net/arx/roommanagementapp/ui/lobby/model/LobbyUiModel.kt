package net.arx.roommanagementapp.ui.lobby.model

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.theme.GridRoomRowBaseHeight
import net.arx.roommanagementapp.ui.theme.SpacingQuarter_4dp
import net.arx.roommanagementapp.ui.user.model.UserUiItem

data class LobbyUiState(
    val date: MutableState<DateUiItem> = mutableStateOf(DateUiItem()),
    val isAdmin: MutableState<Boolean> = mutableStateOf(false),
    val users: SnapshotStateList<UserUiItem> = mutableStateListOf(),
    val rooms: SnapshotStateList<RoomUiItem> = mutableStateListOf(),
    val selectedUser: MutableState<UserUiItem?> = mutableStateOf(null),
    val selectedRoom: MutableState<RoomUiItem?> = mutableStateOf(null),
    val onAddNewUserClicked: (Long?) -> Unit,
    val onDeleteUserClicked: (UserUiItem) -> Unit,
    val onDeleteRoomClicked: (RoomUiItem) -> Unit,
    val onAddNewRoomClicked: () -> Unit,
    val onSubmitFormClicked: () -> Unit,
    val onValidateText: (FieldUiItem) -> Unit,
    val openDialogForm: MutableState<Boolean?> = mutableStateOf(null),
    val formUiItem: MutableState<DialogFormUiItem> = mutableStateOf(DialogFormUiItem.UserForm()),
    val onCloseDialogForm: () -> Unit,
) {
    val gridUiItem: GridUiItem
        get() = GridUiItem(
            roomsSize = rooms.size,
            hasExtraItem = isAdmin.value,
            baseHeight = GridRoomRowBaseHeight
        )
}

data class GridUiItem(
    val baseHeight: Dp,
    val roomsSize: Int,
    val hasExtraItem: Boolean,
    val columns: Int = 5,
    val verticalSpacing: Dp = SpacingQuarter_4dp
) {
    private val totalRooms = if(hasExtraItem) roomsSize + 1 else roomsSize
    private val totalRows = (totalRooms + (columns - 1)) / columns

    val gridHeight: Dp
        get() = totalRows * (baseHeight + verticalSpacing)
}

sealed class DialogFormUiItem(
    val fields: List<FieldUiItem> = emptyList(),
    val descriptionParam: MutableState<String> = mutableStateOf(""),
    @StringRes val title: Int = R.string.empty_string,
    @StringRes val descriptionResId: Int = R.string.empty_string,
    @StringRes val confirmButtonResId: Int = R.string.empty_string,
) {
    val hasError: Boolean
        get() = fields.any { !it.validate() }

    fun resetForm() {
        fields.forEach {
            it.resetField()
        }
    }

    class UserForm: DialogFormUiItem(
        fields = listOf(
            FieldUiItem.UsernameField()
        ),
        title = R.string.lobby_button_add_cleaner_title,
        confirmButtonResId = R.string.form_dialog_confirm_button
    )

    class RoomForm: DialogFormUiItem(
        fields = listOf(
            FieldUiItem.RoomField()
        ),
        title = R.string.lobby_button_add_room_title,
        confirmButtonResId = R.string.form_dialog_confirm_button
    )

    class DeleteRoomForm: DialogFormUiItem(
        title = R.string.form_dialog_delete_title,
        descriptionResId = R.string.form_dialog_delete_room_description,
        confirmButtonResId = R.string.form_dialog_delete_button
    )

    class DeleteUserForm: DialogFormUiItem(
        title = R.string.form_dialog_delete_title,
        descriptionResId = R.string.form_dialog_delete_user_description,
        confirmButtonResId = R.string.form_dialog_delete_button
    )
}

sealed class FieldUiItem(
    @StringRes val label: Int,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val alreadyExists: MutableState<Boolean> = mutableStateOf(false),
    val text: MutableState<String> = mutableStateOf(""),
) {

    fun updateText(text: String) {
        this.text.value = text
    }

    abstract fun validate(): Boolean
    class UsernameField: FieldUiItem(
        label = R.string.form_dialog_cleaner_field_label
    ) {
        override fun validate(): Boolean {
            return text.value.isNotBlank() && !alreadyExists.value
        }
    }

    class RoomField: FieldUiItem(
        label = R.string.form_dialog_room_field_label
    ) {
        override fun validate(): Boolean {
            return text.value.isNotBlank() && !alreadyExists.value
        }
    }

    fun resetField() {
        this.text.value = ""
    }
}