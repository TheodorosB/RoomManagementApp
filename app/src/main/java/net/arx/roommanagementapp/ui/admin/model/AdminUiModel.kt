package net.arx.roommanagementapp.ui.admin.model

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.KeyboardType
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem

data class LobbyUiState(
    val date: MutableState<DateUiItem> = mutableStateOf(DateUiItem()),
    val loggedInUser: MutableState<UserUiItem> = mutableStateOf(UserUiItem()),
    val users: SnapshotStateList<UserUiItem> = mutableStateListOf(),
    val rooms: SnapshotStateList<RoomUiItem> = mutableStateListOf(),
    val onAddNewUserClicked: () -> Unit,
    val onSubmitFormClicked: () -> Unit,
    val onUserClicked: () -> Unit,
    val onRoomClicked: (Long) -> Unit,
    val onAddNewRoomClicked: () -> Unit,
    val openDialogForm: MutableState<Boolean?> = mutableStateOf(null),
    val formUiItem: MutableState<DialogFormUiItem> = mutableStateOf(DialogFormUiItem.User()),
    val onCloseDialogForm: () -> Unit,
)

sealed class DialogFormUiItem(
    val fields: List<FieldUiItem> = emptyList(),
    val dropDownMenus: List<DropDownMenu<*>> = emptyList(),
    @StringRes val title: MutableState<Int> = mutableIntStateOf(R.string.empty_string),
) {
    class User : DialogFormUiItem(
        fields = listOf(
            FieldUiItem.UsernameField(),
            FieldUiItem.PasswordField()
        )
    )

    class Room : DialogFormUiItem(
        fields = listOf(
            FieldUiItem.RoomField()
        )
    )

    class Task(
        val roomId: Long
    ) : DialogFormUiItem(
        dropDownMenus = listOf(
            DropDownMenu.StatusMenu(),
            DropDownMenu.UserMenu()
        )
    )

    fun resetForm() {
        fields.forEach {
            it.resetField()
        }
    }
}

sealed class FieldUiItem(
    @StringRes val label: Int,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val text: MutableState<String> = mutableStateOf(""),
) {
    class UsernameField: FieldUiItem(
        label = R.string.dialog_form_cleaner_field
    )

    class PasswordField: FieldUiItem(
        label = R.string.dialog_form_cleaner_password_field,
        keyboardType = KeyboardType.NumberPassword
    )

    class RoomField: FieldUiItem(
        label = R.string.dialog_form_room_field
    )

    fun onUpdateName(name: String) {
        this.text.value = name
    }

    fun resetField() {
        this.text.value = ""
    }
}

sealed class DropDownMenu<T>(
    @StringRes val label: Int,
    val isExpanded: MutableState<Boolean> = mutableStateOf(false),
    val options: MutableList<T>,
    val selectedOption: MutableState<T>,
) {
    class StatusMenu() : DropDownMenu<RoomCleaningStatus>(
        label = R.string.dialog_form_select_status,
        options = mutableListOf(
            RoomCleaningStatus.General(),
            RoomCleaningStatus.Regular(),
            RoomCleaningStatus.Cleaned()
        ),
        selectedOption = mutableStateOf(RoomCleaningStatus.General())
    )

    class UserMenu(
    ) : DropDownMenu<UserUiItem>(
        label = R.string.dialog_form_select_user,
        options = mutableListOf(),
        selectedOption = mutableStateOf(UserUiItem())
    )

    fun updatedSelectedStatus(status: RoomCleaningStatus) {
        selectedOption.value = status as T
        toggleExpanded()
    }

    fun updatedSelectedUser(user: UserUiItem) {
        selectedOption.value = user as T
        toggleExpanded()
    }

    fun toggleExpanded() {
        isExpanded.value = !isExpanded.value
    }
}