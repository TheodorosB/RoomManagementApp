package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.AdminRoomUiState
import net.arx.roommanagementapp.ui.room.composable.RoomItem
import net.arx.roommanagementapp.ui.room.model.TaskUiItem
import net.arx.roommanagementapp.ui.theme.SpacingDefault_16dp
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.theme.SpacingQuarter_4dp
import net.arx.roommanagementapp.ui.user.composable.TaskItem
import net.arx.roommanagementapp.ui.user.composable.UserItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem

@Composable
internal fun AdminRoomScreen(
    uiState: State<AdminRoomUiState>
) {
    AdminRoomContent(
        uiState = uiState
    )
}

@Composable
private fun AdminRoomContent(
    uiState: State<AdminRoomUiState>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = SpacingDefault_16dp, alignment = Alignment.Top)
    ) {
        RoomItem(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(2.5f),
            roomUiItem = uiState.value.room.value,
            arrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.CenterVertically)
        )

        AdminRoomUsersGrid(
            users = uiState.value.users,
            onUserClicked = uiState.value.onUserClicked
        )

        AdminRoomTasksGrid(
            tasks = uiState.value.room.value.tasks,
            onUpdateStatus = uiState.value.onUpdateStatus
        )
    }
}

@Composable
private fun AdminRoomUsersGrid(
    users: List<UserUiItem>,
    onUserClicked: (Long?) -> Unit
) {
    Text(
        modifier = Modifier.alpha(0.5f),
        text = stringResource(id = R.string.admin_room_user_selection_title),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary
    )

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(0.9f),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(space = SpacingQuarter_4dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = SpacingQuarter_4dp, alignment = Alignment.Top)
    ) {

        items(items = users, key = { it.id ?: 0 }) { user ->
            UserItem(
                userId = user.id,
                name = user.name,
                isClickable = true,
                onUserClicked = onUserClicked,
                isSelected = user.isSelected.value
            )
        }
    }
}

@Composable
private fun AdminRoomTasksGrid(
    tasks: List<TaskUiItem>,
    onUpdateStatus: () -> Unit
) {
    Text(
        modifier = Modifier.alpha(0.5f),
        text = stringResource(id = R.string.admin_room_task_selection_title),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary
    )

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(0.9f),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(space = SpacingQuarter_4dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = SpacingQuarter_4dp, alignment = Alignment.Top)
    ) {

        items(items = tasks, key = { it.title }) { task ->
            TaskItem(
                task = task,
                onUpdateStatus = onUpdateStatus
            )
        }
    }
}