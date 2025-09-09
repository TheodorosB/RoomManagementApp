package net.arx.roommanagementapp.ui.room.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.model.RoomManagementUiState
import net.arx.roommanagementapp.ui.room.model.TaskUiItem
import net.arx.roommanagementapp.ui.room.viewmodel.RoomManagementViewModel
import net.arx.roommanagementapp.ui.user.composable.UserItem
import net.arx.roommanagementapp.ui.user.model.UserUiItem

@Composable
internal fun RoomManagementScreen(
    roomId: Long?,
    date: DateUiItem
) {
    val viewModel: RoomManagementViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = roomId, key2 = date.dayStart.value, key3 = date.dayEnd.value) {
        viewModel.updateRoomDetails(roomId = roomId, date = date)
    }

    RoomManagementContent(
        uiState = uiState
    )
}

@Composable
private fun RoomManagementContent(
    uiState: State<RoomManagementUiState>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 14.dp, alignment = Alignment.Top)
    ) {
        RoomItem(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(2.5f),
            roomUiItem = uiState.value.room.value,
            arrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically)
        )

        RoomManagementUsersGrid(
            users = uiState.value.users,
            onUserClicked = uiState.value.onUserClicked
        )

        RoomManagementTasksGrid(
            tasks = uiState.value.room.value.tasks,
            onUpdateStatus = uiState.value.onUpdateStatus
        )
    }
}

@Composable
private fun RoomManagementUsersGrid(
    users: List<UserUiItem>,
    onUserClicked: (Long?) -> Unit
) {
    Text(
        modifier = Modifier.alpha(0.5f),
        text = stringResource(id = R.string.room_details_user_selection_title),
        fontSize = 30.sp
    )

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(0.8f),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(space = 14.dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = 14.dp, alignment = Alignment.Top)
    ) {

        items(items = users, key = { it.id ?: 0 }) { user ->
            UserItem(
                user = user,
                onUserClicked = onUserClicked
            )
        }
    }
}

@Composable
private fun RoomManagementTasksGrid(
    tasks: List<TaskUiItem>,
    onUpdateStatus: () -> Unit
) {
    Text(
        modifier = Modifier.alpha(0.5f),
        text = stringResource(id = R.string.room_details_task_selection_title),
        fontSize = 30.sp
    )

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(0.8f),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(space = 14.dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = 14.dp, alignment = Alignment.Top)
    ) {

        items(items = tasks, key = { it.title }) { task ->
            TaskItem(
                task = task,
                onUpdateStatus = onUpdateStatus
            )
        }
    }
}