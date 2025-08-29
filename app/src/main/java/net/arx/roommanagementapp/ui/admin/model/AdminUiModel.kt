package net.arx.roommanagementapp.ui.admin.model

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem

data class AdminUiState(
    val cleaners: SnapshotStateList<CleanerUiItem> = mutableStateListOf(),
    val rooms: SnapshotStateList<RoomUiItem> = mutableStateListOf(),
    val onAddNewCleanerClicked: () -> Unit,
    val onSubmitFormClicked: () -> Unit,
    val onCleanerClicked: () -> Unit,
    val onAddNewRoomClicked: () -> Unit,
    val openDialogForm: MutableState<Boolean?> = mutableStateOf(null),
    val formUiItem: MutableState<DialogFormUiItem> = mutableStateOf(DialogFormUiItem.Cleaner()),
    val onCloseAlertDialog: () -> Unit,
)

sealed class DialogFormUiItem(
    val fields: List<FieldUiItem>,
    val dropDownBoxes: List<DropDownBoxUiItem> = listOf()
) {
    class Cleaner : DialogFormUiItem(
        fields = listOf(
            FieldUiItem.CleanerField()
        )
    )

    class Room : DialogFormUiItem(
        fields = listOf(
            FieldUiItem.RoomField()
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
    val text: MutableState<String> = mutableStateOf(""),
) {

    class CleanerField : FieldUiItem(
        label = R.string.dialog_form_cleaner_field,
        text = mutableStateOf("")
    )

    class RoomField : FieldUiItem(
        label = R.string.dialog_form_room_field,
        text = mutableStateOf("")
    )

    fun onUpdateName(name: String) {
        this.text.value = name
    }

    fun resetField() {
        this.text.value = ""
    }
}

data class DropDownBoxUiItem(
    val options: List<String>
)