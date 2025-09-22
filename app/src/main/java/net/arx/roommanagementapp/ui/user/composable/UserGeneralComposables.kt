package net.arx.roommanagementapp.ui.user.composable

import androidx.annotation.DrawableRes
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
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.theme.SpacingCustom_10dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_12dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_24dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_60dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_6dp
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.theme.SpacingQuarter_4dp
import net.arx.roommanagementapp.ui.user.model.UserUiItem

@Composable
internal fun UsersRow(
    isAdmin: Boolean,
    users: List<UserUiItem>,
    onAddNewUserClicked: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(9f)
            .padding(horizontal = SpacingHalf_8dp),
        horizontalArrangement = Arrangement.spacedBy(space = SpacingCustom_10dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items = users, key = { it.id ?: 0 }) { user ->
            UserItem(
                modifier = Modifier.fillMaxWidth(0.2f),
                name = user.name,
                userId = user.id,
                isSelected = user.isSelected.value
            )
        }
        if(isAdmin) {
            item {
                UserItem(
                    name = stringResource(R.string.lobby_button_add_cleaner_title),
                    modifier = Modifier.fillMaxWidth(0.2f),
                    iconResId = R.drawable.ic_add_new_user,
                    isClickable = true,
                    onUserClicked = onAddNewUserClicked
                )
            }
        }
    }
}

@Composable
internal fun UserItem(
    name: String,
    modifier: Modifier = Modifier,
    userId: Long? = null,
    isClickable: Boolean = false,
    isSelected: Boolean = false,
    @DrawableRes iconResId: Int = R.drawable.ic_cleaner_profile,
    onUserClicked: (Long?) -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(all = SpacingCustom_6dp)
            .shadow(
                elevation = SpacingCustom_6dp,
                shape = RoundedCornerShape(SpacingCustom_24dp)
            )
            .border(
                width = SpacingQuarter_4dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(SpacingCustom_24dp)
            )
            .clip(shape = RoundedCornerShape(SpacingCustom_24dp))
            .background(
                color = if(isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onBackground
            )
            .clickable(enabled = isClickable) {
                onUserClicked(userId)
            }
            .padding(all = SpacingCustom_12dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(SpacingCustom_60dp),
            painter = painterResource(id = iconResId),
            tint = MaterialTheme.colorScheme.scrim,
            contentDescription = null
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.scrim
        )
    }
}