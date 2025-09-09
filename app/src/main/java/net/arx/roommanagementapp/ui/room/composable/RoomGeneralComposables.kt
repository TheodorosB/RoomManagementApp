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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.composable.text.AutoSizeText
import net.arx.roommanagementapp.ui.room.model.RoomUiItem

@Composable
fun RoomsRow(
    rooms: List<RoomUiItem>,
    isAdmin: Boolean,
    onRoomClicked: (Long) -> Unit,
    onAddNewRoomClicked: () -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        columns = GridCells.Fixed(5),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top)
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
fun RoomItem(
    roomUiItem: RoomUiItem,
    arrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier,
    onRoomClicked: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(40.dp))
            .background(Color.White)
            .clickable(enabled = roomUiItem.isClickable) {
                onRoomClicked(roomUiItem.id)
            }
            .padding(horizontal = 14.dp, vertical = 7.dp),
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
            Spacer(modifier = Modifier.height(10.dp))
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.CenterHorizontally),
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
fun AddNewRoomItem(
    onAddNewRoomClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(40.dp))
            .clickable {
                onAddNewRoomClicked()
            }
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.CenterVertically)
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .aspectRatio(1f),
            imageVector = Icons.Outlined.AddCircle,
            contentDescription = null,
            tint = Color.Black
        )
        Text(
            text = stringResource(R.string.add_room_title),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
    }
}