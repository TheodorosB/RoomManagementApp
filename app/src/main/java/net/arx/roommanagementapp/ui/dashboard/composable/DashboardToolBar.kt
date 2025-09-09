package net.arx.roommanagementapp.ui.dashboard.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.base.compose.noRippleClickable
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.theme.ColorBaseGrey
import net.arx.roommanagementapp.util.ext.formatDate

@Composable
fun DashboardToolBar(
    date: DateUiItem,
    hasBackButton: Boolean,
    modifier: Modifier = Modifier,
    onPreviousDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    onOpenAdminPinFormClicked: () -> Unit,
    onOpenUserPinFormClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onHomeScreenClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(8f)
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        DashboardBackButton(
            modifier = Modifier.weight(0.3f),
            hasBackButton = hasBackButton,
            onBackButtonClicked = onBackButtonClicked
        )

        DashboardDateRow(
            modifier = Modifier.weight(0.4f),
            date = date.dayStart.value.formatDate(),
            onNextDateClick = onNextDateClick,
            onPreviousDateClick = onPreviousDateClick
        )

        DashboardActionsRow(
            modifier = Modifier.weight(0.3f),
            onOpenAdminPinFormClicked = onOpenAdminPinFormClicked,
            onOpenCleanerPinFormClicked = onOpenUserPinFormClicked,
            onHomeScreenClicked = onHomeScreenClicked
        )
    }
}

@Composable
fun DashboardDateRow(
    date: String,
    onPreviousDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .aspectRatio(4.5f)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
            .background(Color.White)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .weight(0.15f)
                .aspectRatio(1f)
                .clickable {
                    onPreviousDateClick()
                },
            tint = ColorBaseGrey,
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = date,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Icon(
            modifier = Modifier
                .weight(0.15f)
                .aspectRatio(1f)
                .clickable {
                    onNextDateClick()
                },
            imageVector = Icons.Filled.KeyboardArrowRight,
            tint = ColorBaseGrey,
            contentDescription = null,
        )
    }
}

@Composable
fun DashboardBackButton(
    hasBackButton: Boolean,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if(hasBackButton) {
            Icon(
                modifier = Modifier
                    .size(70.dp)
                    .noRippleClickable {
                        onBackButtonClicked()
                    },
                painter = painterResource(id = R.drawable.ic_back_button),
                tint = Color.Black,
                contentDescription = null
            )
        }
    }
}

@Composable
fun DashboardActionsRow(
    modifier: Modifier = Modifier,
    onOpenAdminPinFormClicked: () -> Unit,
    onOpenCleanerPinFormClicked: () -> Unit,
    onHomeScreenClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.End),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            modifier = Modifier
                .size(70.dp)
                .clickable {
                    onOpenAdminPinFormClicked()
                },
            imageVector = Icons.Outlined.DateRange,
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .size(70.dp)
                .clickable {
                    onOpenCleanerPinFormClicked()
                },
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .size(70.dp)
                .clickable {
                    onHomeScreenClicked()
                },
            imageVector = Icons.Outlined.Home,
            contentDescription = null
        )
    }
}