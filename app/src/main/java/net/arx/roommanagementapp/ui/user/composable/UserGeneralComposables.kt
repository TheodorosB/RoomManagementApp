package net.arx.roommanagementapp.ui.user.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.base.compose.ifable
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground
import net.arx.roommanagementapp.ui.user.model.UserUiItem

@Composable
fun UsersRow(
    isAdmin: Boolean,
    users: List<UserUiItem>,
    onUserClicked: () -> Unit,
    onAddNewUserClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(9f)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items = users, key = { it.id ?: 0}) { user ->
            UserItem(
                modifier = Modifier.fillMaxWidth(0.2f),
                user = user,
                onUserClicked = onUserClicked
            )
        }
        if(isAdmin) {
            item {
                AddNewUserItem(
                    modifier = Modifier.fillMaxWidth(0.2f),
                    onAddNewUserClicked = onAddNewUserClicked
                )
            }
        }
    }
}

@Composable
fun UserItem(
    user: UserUiItem,
    modifier: Modifier = Modifier,
    onUserClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(25.dp)
            )
            .border(
                width = 3.dp,
                color = Color.White,
                shape = RoundedCornerShape(25.dp)
            )
            .clip(shape = RoundedCornerShape(25.dp))
            .background(user.backgroundColor)
            .ifable(condition = user.isClickable) {
                clickable {
                    onUserClicked()
                }
            }
            .padding(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            imageVector = user.icon,
            contentDescription = null
        )
        Text(
            text = user.name,
            fontSize = 30.sp,
        )
    }
}

@Composable
fun AddNewUserItem(
    modifier: Modifier = Modifier,
    onAddNewUserClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(25.dp)
            )
            .border(
                width = 3.dp,
                color = Color.White,
                shape = RoundedCornerShape(25.dp)
            )
            .clip(shape = RoundedCornerShape(25.dp))
            .background(ColorBaseBackground)
            .clickable {
                onAddNewUserClicked()
            }
            .padding(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            imageVector = Icons.Outlined.Add,
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.add_cleaner_title),
            fontSize = 30.sp,
        )
    }
}