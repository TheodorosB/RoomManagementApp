package net.arx.roommanagementapp.ui.admin.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.util.ext.formatDate
import java.util.Calendar

data class AdminUiState(
    val onPinPanelIconClicked: () -> Unit,
    val onHomeScreenClicked: () -> Unit,
    val date: MutableState<String> = mutableStateOf(Calendar.getInstance().formatDate()),
    val cleaners: SnapshotStateList<CleanerUiItem> = mutableStateListOf(),
    val onAddNewCleanerClicked: () -> Unit,
    val onAddCleanerClicked: () -> Unit,
    val onCleanerClicked: () -> Unit,
    val openAlertDialog: MutableState<Boolean?> = mutableStateOf(null),
    val cleanerFormUiItem: DialogFormUiItem = DialogFormUiItem(),
    val onCloseAlertDialog: () -> Unit,
) {
    private val calendar = Calendar.getInstance()

    fun onPreviousDateClicked() {
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        date.value = calendar.formatDate()
    }

    fun onNextDateClicked() {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        date.value = calendar.formatDate()
    }
}

data class DialogFormUiItem(
    val name: MutableState<String> = mutableStateOf(""),
) {
    fun onUpdateName(name: String) {
        this.name.value = name
    }

    fun resetForm() {
        this.name.value = ""
    }
}