package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.arx.roommanagementapp.ui.admin.viewmodel.AdminViewModel
import net.arx.roommanagementapp.ui.theme.ColorBlueBackgroundColor

@Composable
fun AdminScreen(
    navigateToCleaner: () -> Unit,
) {

    val viewModel: AdminViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top)
    ) {
        RoomManagementToolBar(
            date = uiState.value.date.value,
            onPreviousDateClick = { uiState.value.onPreviousDateClicked() },
            onNextDateClick = { uiState.value.onNextDateClicked() },
            onPinPanelIconClicked = uiState.value.onPinPanelIconClicked,
            onUserIconClicked = uiState.value.onUserIconClicked,
            onHomeScreenClicked = uiState.value.onHomeScreenClicked,
        )

        Text(text = "This is Admin Screen", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(all = 4.dp),
            onClick = {
                navigateToCleaner()
            }
        ) {
            Text(text = "Go to Cleaner Screen", fontSize = 15.sp)
        }
    }
}

@Composable
fun RoomManagementToolBar(
    date: String,
    modifier: Modifier = Modifier,
    onPreviousDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    onPinPanelIconClicked: () -> Unit,
    onUserIconClicked: () -> Unit,
    onHomeScreenClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(8f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(0.3f))

        RoomManagementDateRow(
            modifier = Modifier.weight(0.4f),
            date = date,
            onNextDateClick = onNextDateClick,
            onPreviousDateClick = onPreviousDateClick
        )

        RoomManagementActionsRow(
            modifier = Modifier.weight(0.3f),
            onPinPanelIconClicked = onPinPanelIconClicked,
            onUserIconClicked = onUserIconClicked,
            onHomeScreenClicked = onHomeScreenClicked
        )
    }

}

@Composable
fun RoomManagementDateRow(
    date: String,
    onPreviousDateClick: () -> Unit,
    onNextDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 0.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .background(Color.White),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .weight(0.2f)
                .aspectRatio(1f)
                .clickable {
                    onPreviousDateClick()
                },
            tint = ColorBlueBackgroundColor,
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
                .weight(0.2f)
                .aspectRatio(1f)
                .clickable {
                    onNextDateClick()
                },
            imageVector = Icons.Filled.KeyboardArrowRight,
            tint = ColorBlueBackgroundColor,
            contentDescription = null,
        )
    }
}

@Composable
fun RoomManagementActionsRow(
    modifier: Modifier = Modifier,
    onPinPanelIconClicked: () -> Unit,
    onUserIconClicked: () -> Unit,
    onHomeScreenClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(top = 8.dp)
            .aspectRatio(4f),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.End),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            modifier = Modifier
                .weight(0.3f)
                .aspectRatio(1f)
                .clickable {
                    onPinPanelIconClicked()
                },
            imageVector = Icons.Outlined.DateRange,
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .weight(0.3f)
                .aspectRatio(1f)
                .clickable {
                    onUserIconClicked()
                },
            imageVector = Icons.Outlined.AccountBox,
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .weight(0.3f)
                .aspectRatio(1f)
                .clickable {
                    onHomeScreenClicked()
                },
            imageVector = Icons.Outlined.Home,
            contentDescription = null
        )
    }
}