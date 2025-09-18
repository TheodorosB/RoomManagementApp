package net.arx.roommanagementapp.ui.room.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.composable.text.AutoSizeText
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.theme.SpacingCustom_10dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_14dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_18dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_36dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_6dp
import net.arx.roommanagementapp.ui.theme.SpacingDefault_16dp
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.theme.SpacingQuarter_4dp

@Composable
internal fun RoomsRow(
    rooms: List<RoomUiItem>,
    isAdmin: Boolean,
    onRoomClicked: (Long) -> Unit,
    onAddNewRoomClicked: () -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpacingCustom_18dp),
        columns = GridCells.Fixed(5),
        horizontalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.Top)
    ) {
        items(items = rooms, key = { it.id }) { room ->
            RoomItem(
                modifier = Modifier.aspectRatio(1f),
                roomUiItem = room,
                onRoomClicked = onRoomClicked,
                arrangement = Arrangement.SpaceBetween
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
    onRoomClicked: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(SpacingCustom_36dp))
            .background(Color.White)
            .clickable(enabled = roomUiItem.isClickable) {
                onRoomClicked(roomUiItem.id)
            }
            .padding(horizontal = SpacingCustom_14dp, vertical = SpacingCustom_6dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = arrangement
    ) {
        if(roomUiItem.hasStatus) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f),
                imageVector = roomUiItem.statusIcon,
                contentDescription = null,
                tint = roomUiItem.statusColor
            )
            AutoSizeText(
                modifier = Modifier.alpha(0.6f),
                text = roomUiItem.user.name,
                textAlign = TextAlign.Center,
                maxFontSize = 40.sp
            )
        } else {
            Spacer(modifier = Modifier.height(SpacingCustom_10dp))
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = SpacingQuarter_4dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .weight(0.2f)
                    .aspectRatio(1f),
                painter = painterResource(roomUiItem.roomIcon),
                tint = roomUiItem.roomIconColor,
                contentDescription = null
            )
            AutoSizeText(
                modifier = Modifier
                    .weight(0.5f),
                text = roomUiItem.name,
                textAlign = TextAlign.Center,
                maxFontSize = 50.sp
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
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(SpacingCustom_36dp))
            .clickable { onAddNewRoomClicked() }
            .background(Color.White)
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
            tint = Color.Black
        )
        Text(
            text = stringResource(R.string.lobby_button_add_room_title),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}