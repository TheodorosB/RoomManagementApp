package net.arx.roommanagementapp.ui.dashboard.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.theme.ColorBaseGrey
import net.arx.roommanagementapp.ui.theme.SpacingCustom_14dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_20dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_40dp
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.util.ext.formatDate

@Composable
internal fun DashboardToolBar(
    date: DateUiItem,
    hasBackButton: Boolean,
    modifier: Modifier = Modifier,
    onPreviousDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    onOpenAdminPinFormClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(11f)
            .padding(horizontal = SpacingCustom_14dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {

        ToolbarActionButton(
            modifier = Modifier.weight(0.3f),
            painter = painterResource(id = R.drawable.ic_back_button),
            hasButton = hasBackButton,
            arrangement = Arrangement.Start,
            onButtonClicked = onBackButtonClicked
        )

        ToolbarDateRow(
            modifier = Modifier.weight(0.4f),
            date = date.dayStart.value.formatDate(),
            onNextDateClick = onNextDateClick,
            onPreviousDateClick = onPreviousDateClick
        )

        ToolbarActionButton(
            modifier = Modifier.weight(0.3f),
            hasButton = true,
            arrangement = Arrangement.End,
            painter = painterResource(id = R.drawable.ic_admin_profile),
            onButtonClicked = onOpenAdminPinFormClicked
        )
    }
}

@Composable
private fun ToolbarDateRow(
    date: String,
    onPreviousDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .aspectRatio(4.5f)
            .shadow(
                elevation = SpacingHalf_8dp,
                shape = RoundedCornerShape(
                    bottomStart = SpacingCustom_40dp,
                    bottomEnd = SpacingCustom_40dp
                )
            )
            .background(Color.White)
            .padding(horizontal = SpacingCustom_20dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        ToolbarDateIcon(
            modifier = Modifier.weight(0.15f),
            painter = painterResource(id = R.drawable.ic_left_arrow),
            onButtonClicked = onPreviousDateClick
        )

        Text(
            modifier = Modifier.weight(1f),
            text = date,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        ToolbarDateIcon(
            modifier = Modifier.weight(0.15f),
            painter = painterResource(id = R.drawable.ic_right_arrow),
            onButtonClicked = onNextDateClick
        )
    }
}

@Composable
private fun ToolbarDateIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    onButtonClicked: () -> Unit
) {
    Icon(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onButtonClicked() },
        tint = ColorBaseGrey,
        painter = painter,
        contentDescription = null
    )
}

@Composable
private fun ToolbarActionButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    hasButton: Boolean,
    arrangement: Arrangement.Horizontal,
    onButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = arrangement
    ) {
        if (hasButton) {
            Icon(
                modifier = Modifier
                    .padding(all = SpacingHalf_8dp)
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1f)
                    .clickable {
                        onButtonClicked()
                    },
                painter = painter,
                tint = Color.Black,
                contentDescription = null
            )
        }
    }
}