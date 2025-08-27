package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.admin.viewmodel.AdminViewModel
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground
import net.arx.roommanagementapp.ui.theme.ColorBaseGrey

@Composable
fun AdminScreen(
    navigateToCleaner: () -> Unit,
) {

    val viewModel: AdminViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AdminContent(
        uiState = uiState,
        navigateToCleaner = navigateToCleaner
    )
}

@Composable
fun AdminContent(
    uiState: State<AdminUiState>,
    navigateToCleaner: () -> Unit,
) {
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
            onUserIconClicked = navigateToCleaner,
            onHomeScreenClicked = uiState.value.onHomeScreenClicked,
        )

        CleanersRow(
            cleaners = uiState.value.cleaners,
            onCleanerClicked = uiState.value.onCleanerClicked,
            onAddNewCleanerClicked = uiState.value.onAddNewCleanerClicked
        )
    }

    if(uiState.value.openAlertDialog.value == true) {
        CleanerDialogForm(
            formUiItem = uiState.value.cleanerFormUiItem,
            onDismissRequest = uiState.value.onCloseAlertDialog,
            onAddCleanerClicked = uiState.value.onAddCleanerClicked
        )
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
            .aspectRatio(8f)
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
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
fun CleanersRow(
    cleaners: List<CleanerUiItem>,
    onCleanerClicked: () -> Unit,
    onAddNewCleanerClicked: () -> Unit,
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
        items(cleaners, key = { it.name }) { cleaner ->
            CleanerItem(
                modifier = Modifier.fillMaxWidth(0.2f),
                name = cleaner.name,
                icon = cleaner.icon,
                onItemClicked = onCleanerClicked
            )
        }
        item {
            CleanerItem(
                modifier = Modifier.fillMaxWidth(0.2f),
                name = stringResource(R.string.add_cleaner_title),
                icon = Icons.Outlined.Add,
                onItemClicked = onAddNewCleanerClicked
            )
        }
    }
}

@Composable
fun CleanerItem(
    name: String,
    icon: ImageVector,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 3.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(ColorBaseBackground)
            .clickable {
                onItemClicked()
            }
            .padding(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            imageVector = icon,
            contentDescription = null
        )
        Text(
            text = name,
            fontSize = 30.sp,
        )
    }
}

@Composable
fun CleanerDialogForm(
    formUiItem: DialogFormUiItem,
    onDismissRequest: () -> Unit,
    onAddCleanerClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(2f)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            TextField(
                value = formUiItem.name.value,
                onValueChange = { formUiItem.onUpdateName(it) },
                label = {
                    Text(
                        text = stringResource(R.string.add_cleaner_dialog_label),
                        fontSize = 14.sp
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .width(intrinsicSize = IntrinsicSize.Min)
                        .padding(all = 4.dp),
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.add_cleaner_dialog_cancel),
                        fontSize = 15.sp
                    )
                }

                Button(
                    modifier = Modifier
                        .width(intrinsicSize = IntrinsicSize.Min)
                        .padding(all = 4.dp),
                    onClick = {
                        onAddCleanerClicked()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.add_cleaner_dialog_confirm),
                        fontSize = 15.sp
                    )
                }
            }
        }
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
fun RoomManagementActionsRow(
    modifier: Modifier = Modifier,
    onPinPanelIconClicked: () -> Unit,
    onUserIconClicked: () -> Unit,
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
                .size(60.dp)
                .clickable {
                    onPinPanelIconClicked()
                },
            imageVector = Icons.Outlined.DateRange,
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clickable {
                    onUserIconClicked()
                },
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clickable {
                    onHomeScreenClicked()
                },
            imageVector = Icons.Outlined.Home,
            contentDescription = null
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
private fun AdminContentPreview() {
    AdminContent(
        uiState = remember { mutableStateOf(
            AdminUiState(
                cleaners = mutableStateListOf(
                    CleanerUiItem(name = "Γεωργία"),
                    CleanerUiItem(name = "Άννα"),
                    CleanerUiItem(name = "Ιωάννα"),
                    CleanerUiItem(name = "Δήμητρα"),
                    CleanerUiItem(name = "Ελένη")
                ),
                onPinPanelIconClicked = {},
                onHomeScreenClicked = {},
                onCleanerClicked = {},
                onAddCleanerClicked = {},
                onCloseAlertDialog = {},
                onAddNewCleanerClicked = {}
            ))},
        navigateToCleaner = {}
    )
}