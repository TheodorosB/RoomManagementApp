package net.arx.roommanagementapp.ui.room.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.lobby.model.GridUiItem
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.theme.SpacingCustom_10dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_18dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_36dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_40dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_60dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_6dp
import net.arx.roommanagementapp.ui.theme.SpacingDefault_16dp
import net.arx.roommanagementapp.ui.theme.SpacingEighth_2dp
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.theme.SpacingQuarter_4dp

@Composable
internal fun RoomsRow(
    rooms: List<RoomUiItem>,
    gridUiItem: GridUiItem,
    isAdmin: Boolean,
    onRoomClicked: (Long) -> Unit,
    onDeleteRoomClicked: (RoomUiItem) -> Unit,
    onAddNewRoomClicked: () -> Unit
) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(gridUiItem.gridHeight)
            .padding(horizontal = SpacingCustom_18dp),
        columns = GridCells.Fixed(gridUiItem.columns),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(space = SpacingDefault_16dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = gridUiItem.verticalSpacing, alignment = Alignment.Top)
    ) {
        items(items = rooms, key = { it.id }) { room ->
            RoomItem(
                modifier = Modifier.aspectRatio(0.95f),
                roomUiItem = room,
                isRoomDeletable = isAdmin,
                arrangement = Arrangement.SpaceBetween,
                onRoomClicked = onRoomClicked,
                onDeleteRoomClicked = { onDeleteRoomClicked(room) }
            )
        }
        if(isAdmin) {
            item {
                AddNewRoomItem(
                    onAddNewRoomClicked = onAddNewRoomClicked
                )
            }
        }
    }
}

@Composable
internal fun RoomItem(
    roomUiItem: RoomUiItem,
    arrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier,
    isRoomDeletable: Boolean = false,
    onRoomClicked: (Long) -> Unit = {},
    onDeleteRoomClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = SpacingCustom_10dp)
            .shadow(
                elevation = SpacingCustom_6dp,
                shape = RoundedCornerShape(SpacingCustom_36dp)
            )
            .border(
                width = SpacingEighth_2dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(SpacingCustom_36dp)
            )
            .clip(shape = RoundedCornerShape(SpacingCustom_36dp))
            .background(color = MaterialTheme.colorScheme.onBackground)
            .clickable(enabled = roomUiItem.isClickable) {
                onRoomClicked(roomUiItem.id)
            }
            .padding(horizontal = SpacingHalf_8dp, vertical = SpacingDefault_16dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = arrangement
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(SpacingCustom_40dp))
            if(roomUiItem.hasStatus) {
                Icon(
                    modifier = Modifier.size(SpacingCustom_60dp),
                    imageVector = roomUiItem.statusIcon,
                    contentDescription = null,
                    tint = roomUiItem.statusColor
                )
            } else {
                Spacer(modifier = Modifier.width(SpacingCustom_60dp))
            }

            if(isRoomDeletable) {
                Icon(
                    modifier = Modifier
                        .size(SpacingCustom_60dp)
                        .clickable {
                            onDeleteRoomClicked()
                        },
                    painter = painterResource(id = R.drawable.ic_delete),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            } else {
                Spacer(modifier = Modifier.width(SpacingCustom_60dp))
            }
        }

        if(roomUiItem.user.name.isNotBlank()) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f),
                text = roomUiItem.user.name,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                ),
                autoSize = TextAutoSize.StepBased()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f),
                painter = painterResource(roomUiItem.roomIcon),
                tint = roomUiItem.roomIconColor,
                contentDescription = null
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = roomUiItem.name,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.scrim
                ),
                autoSize = TextAutoSize.StepBased()
            )
        }
    }
}


@Composable
private fun AddNewRoomItem(
    onAddNewRoomClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.95f)
            .padding(bottom = SpacingCustom_10dp)
            .shadow(
                elevation = SpacingCustom_6dp,
                shape = RoundedCornerShape(SpacingCustom_36dp)
            )
            .border(
                width = SpacingEighth_2dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(SpacingCustom_36dp)
            )
            .clip(shape = RoundedCornerShape(SpacingCustom_36dp))
            .clickable { onAddNewRoomClicked() }
            .background(color = MaterialTheme.colorScheme.onBackground)
            .padding(horizontal = SpacingHalf_8dp, vertical = SpacingQuarter_4dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = SpacingDefault_16dp, alignment = Alignment.CenterVertically)
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.ic_add_new_room),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.scrim
        )
        Text(
            text = stringResource(R.string.lobby_button_add_room_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.scrim,
            textAlign = TextAlign.Center
        )
    }
}