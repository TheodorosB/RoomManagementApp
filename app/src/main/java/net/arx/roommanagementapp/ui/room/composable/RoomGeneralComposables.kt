package net.arx.roommanagementapp.ui.room.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
        items(rooms, key = { it.name }) { room ->
            RoomItem(
                roomUiItem = room,
                alignment = Alignment.Bottom
            )
        }
        item {
            AddNewRoomItem(
                onAddNewRoomClicked = onAddNewRoomClicked
            )
        }
    }
}

@Composable
fun RoomItem(
    roomUiItem: RoomUiItem,
    alignment: Alignment.Vertical,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(40.dp))
            .background(Color.White)
            .padding(all = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = alignment)
    ) {
        if(roomUiItem.statusIsVisible) {
            Icon(
                imageVector = roomUiItem.statusIcon,
                contentDescription = null,
                tint = roomUiItem.statusColor
            )
            AutoSizeText(
                text = roomUiItem.cleaner.name,
                textAlign = TextAlign.Center,
                maxFontSize = 14.sp
            )
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .weight(0.3f)
                    .aspectRatio(1f),
                painter = painterResource(roomUiItem.status.value.icon),
                tint = roomUiItem.status.value.color,
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