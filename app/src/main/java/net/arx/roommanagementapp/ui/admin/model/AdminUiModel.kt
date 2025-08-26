package net.arx.roommanagementapp.ui.admin.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import net.arx.roommanagementapp.util.ext.formatDate
import java.util.Calendar

data class AdminUiState(
    val onPinPanelIconClicked: () -> Unit = {},
    val onUserIconClicked: () -> Unit = {},
    val onHomeScreenClicked: () -> Unit = {},
    val date: MutableState<String> = mutableStateOf(Calendar.getInstance().formatDate())
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