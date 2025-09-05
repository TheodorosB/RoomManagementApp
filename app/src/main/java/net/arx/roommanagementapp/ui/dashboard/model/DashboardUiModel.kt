package net.arx.roommanagementapp.ui.dashboard.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.util.ext.dayBounds
import net.arx.roommanagementapp.util.ext.formatDate
import java.util.Calendar
import kotlin.text.filter

data class DashboardUiState(
    val date: MutableState<DateUiItem> = mutableStateOf(DateUiItem()),
    val navScreens: List<DashboardNavEntries> = listOf(
        DashboardNavEntries.Admin,
        DashboardNavEntries.Cleaner
    ),
    val backstackEntries: SnapshotStateList<DashboardNavEntries> = mutableStateListOf(
        DashboardNavEntries.Admin
    ),
    val loggedInUser: MutableState<UserUiItem> = mutableStateOf(UserUiItem()),
    val openAdminPinForm: () -> Unit,
    val openCleanerPinForm: () -> Unit,
    val pinFormUiItem: PinFormUiItem = PinFormUiItem(),
    val openPinDialog: MutableState<Boolean> = mutableStateOf(false),
    val onPinDialogDismiss: () -> Unit,
    val onPinComplete: () -> Unit,
) {
    private val calendar = Calendar.getInstance()

    fun onPreviousDateClicked() {
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        updateDate()
    }

    fun onNextDateClicked() {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        updateDate()
    }

    private fun updateDate() {
        date.value.dayStart.value = calendar.dayBounds().first
        date.value.dayEnd.value = calendar.dayBounds().second
    }
}

data class DateUiItem(
    val dayStart: MutableState<Long> = mutableLongStateOf(Calendar.getInstance().dayBounds().first),
    val dayEnd: MutableState<Long> = mutableLongStateOf(Calendar.getInstance().dayBounds().second),
)

data class PinFormUiItem(
    private val length: Int = 4,
    @DrawableRes val title: MutableState<Int> = mutableIntStateOf(R.string.empty_string),
    val text: MutableState<String> = mutableStateOf(""),
    val isError: MutableState<Boolean> = mutableStateOf(false)
) {
    val isComplete: Boolean
        get() = text.value.length == length

    val pinCode: List<PinCodeUiItem>
        get() = (0 until length).map { index ->
            val digit = text.value.getOrNull(index)
            PinCodeUiItem(
                text = digit?.takeIf { it.isDigit() } ?: ' ',
                isFilled = digit != null
            )
        }

    fun update(newText: String) {
        text.value = newText.filter { it.isDigit() }.take(length)
        isError.value = false
    }

    fun clear() {
        text.value = ""
    }
}

data class PinCodeUiItem(
    val text: Char = ' ',
    val isFilled: Boolean = false
)

sealed class DashboardNavEntries() {
    object Admin : DashboardNavEntries()
    object Cleaner : DashboardNavEntries()
}

