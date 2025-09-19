package net.arx.roommanagementapp.ui.user.composable

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.room.composable.RoomItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.room.model.TaskUiItem
import net.arx.roommanagementapp.ui.theme.SpacingCustom_24dp
import net.arx.roommanagementapp.ui.theme.SpacingEighth_2dp
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.theme.SpacingQuarter_4dp
import net.arx.roommanagementapp.ui.user.model.UserRoomUiState

@Composable
internal fun UserRoomScreen(
    uiState: State<UserRoomUiState>
) {
    UserRoomContent(
        uiState = uiState,
    )
}

@Composable
private fun UserRoomContent(
    uiState: State<UserRoomUiState>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.Top)
    ) {
        UserItem(
            userId = uiState.value.user.value.id,
            name = uiState.value.user.value.name,
            backgroundColor = uiState.value.user.value.backgroundColor,
            iconResId = R.drawable.ic_cleaner_profile
        )

        Text(
            modifier = Modifier.alpha(0.5f),
            text = stringResource(id = R.string.user_room_tasks_title),
            style = MaterialTheme.typography.bodyMedium
        )

        UserRoomRow(
            room = uiState.value.room.value,
            onUpdateStatus = uiState.value.onUpdateStatus
        )
    }
}

@Composable
private fun UserRoomRow(
    room: RoomUiItem,
    onUpdateStatus: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(6.5f),
        horizontalArrangement = Arrangement.spacedBy(space = SpacingQuarter_4dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoomItem(
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .aspectRatio(1f),
            roomUiItem = room,
            arrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.CenterVertically)
        )

        Icon(
            modifier = Modifier
                .fillMaxWidth(0.05f)
                .aspectRatio(1f),
            painter = painterResource(R.drawable.ic_right_arrow),
            contentDescription = null
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(0.8f),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.Top)
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
            .clip(shape = RoundedCornerShape(SpacingCustom_24dp))
            .background(Color.White)
            .clickable {
                task.onTaskClicked()
                onUpdateStatus()
            }
            .padding(all = SpacingEighth_2dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = task.title),
            textDecoration = task.textDecoration,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}