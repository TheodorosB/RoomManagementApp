package net.arx.roommanagementapp.ui.room.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.room.model.TaskUiItem
import net.arx.roommanagementapp.ui.user.composable.UserItem
import net.arx.roommanagementapp.ui.user.model.RoomTasksUiState
import net.arx.roommanagementapp.ui.user.viewmodel.RoomTasksViewModel

@Composable
internal fun RoomTasksScreen(
    roomId: Long?,
    date: DateUiItem
) {

    val viewModel: RoomTasksViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = roomId, key2 = date.dayStart.value) {
        viewModel.loadUserData(
            date = date,
            roomId = roomId
        )
    }

    RoomTasksContent(
        uiState = uiState,
    )
}

@Composable
private fun RoomTasksContent(
    uiState: State<RoomTasksUiState>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top)
    ) {
        UserItem(
            user = uiState.value.user.value
        )

        Text(
            modifier = Modifier.alpha(0.5f),
            text = stringResource(id = R.string.cleaner_screen_tasks),
            fontSize = 25.sp
        )

        UserRoomItem(
            room = uiState.value.room.value,
            onUpdateStatus = uiState.value.onUpdateStatus
        )
    }
}

@Composable
private fun UserRoomItem(
    room: RoomUiItem,
    onUpdateStatus: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(6.5f),
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoomItem(
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .aspectRatio(1f),
            roomUiItem = room,
            arrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically)
        )

        Icon(
            modifier = Modifier
                .fillMaxWidth(0.05f)
                .aspectRatio(1f),
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(0.8f),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top)
        ) {

            items(items = room.tasks, key = { it.title }) { task ->
                TaskItem(
                    task = task,
                    onUpdateStatus = onUpdateStatus
                )
            }
        }

        Spacer(modifier = Modifier.fillMaxWidth(0.3f))

        Icon(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            imageVector = room.statusIcon,
            tint = room.statusColor,
            contentDescription = null
        )
    }
}

@Composable
internal fun TaskItem(
    task: TaskUiItem,
    onUpdateStatus: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f)
            .clip(shape = RoundedCornerShape(25.dp))
            .background(Color.White)
            .clickable {
                task.onTaskClicked()
                onUpdateStatus()
            }
            .padding(all = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = task.title),
            textDecoration = task.textDecoration,
            fontSize = 25.sp
        )
    }
}