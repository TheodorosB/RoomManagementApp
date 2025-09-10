package net.arx.roommanagementapp.ui.room.model

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.theme.ColorRoomCleanedStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomGeneralStatus
import net.arx.roommanagementapp.ui.theme.ColorRoomRegularStatus

sealed class TaskUiItem(
    @StringRes val title: Int,
    val color: Color,
    val isDone: MutableState<Boolean>
) {
    val textDecoration: TextDecoration
        get() = if (isDone.value) TextDecoration.LineThrough else TextDecoration.None

    fun onTaskClicked() {
        isDone.value = !isDone.value
    }

    class Mopping(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_mopping_title,
        color = ColorRoomGeneralStatus,
        isDone = isDone
    )

    class Sweeping(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_sweeping_title,
        color = ColorRoomRegularStatus,
        isDone = isDone
    )

    class Garbages(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_garbages_title,
        color = ColorRoomCleanedStatus,
        isDone = isDone
    )

    class Disposables(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_disposables_title,
        color = ColorRoomCleanedStatus,
        isDone = isDone
    )

    class Beddings(
        isDone: MutableState<Boolean> = mutableStateOf(true)
    ) : TaskUiItem(
        title = R.string.task_beddings_title,
        color = ColorRoomCleanedStatus,
        isDone = isDone
    )
}