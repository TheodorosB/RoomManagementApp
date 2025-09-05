package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.admin.model.DropDownMenu
import net.arx.roommanagementapp.ui.admin.model.FieldUiItem
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.user.model.UserUiItem

@Composable
internal fun FormDialog(
    formUiItem: DialogFormUiItem,
    onDismissRequest: () -> Unit,
    onAddCleanerClicked: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(0.9f),
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = formUiItem.title.value),
                    fontSize = 20.sp
                )
                Icon(
                    modifier = Modifier
                        .fillMaxWidth(0.15f)
                        .aspectRatio(1f)
                        .clickable {
                            onDismissRequest()
                        },
                    painter = painterResource(R.drawable.ic_room_management_close),
                    contentDescription = null
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically)
            ) {
                FormDialogFields(
                    fields = formUiItem.fields
                )

                FormDialogMenus(
                    menus = formUiItem.dropDownMenus
                )
            }
        },
        dismissButton = {
            DialogFormButton(
                text = stringResource(R.string.dialog_form_cancel_button),
                onClick = onDismissRequest
            )
        },
        confirmButton = {
            DialogFormButton(
                text = stringResource(R.string.dialog_form_confirm_button),
                onClick = onAddCleanerClicked
            )
        }
    )
}

@Composable
private fun FormDialogFields(
    fields: List<FieldUiItem>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        items(items = fields) { field ->
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                value = field.text.value,
                onValueChange = { field.onUpdateName(it) },
                label = {
                    Text(
                        text = stringResource(field.label),
                        fontSize = 16.sp
                    )
                },
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = field.keyboardType,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )
        }
    }
}

@Composable
private fun FormDialogMenus(
    menus: List<DropDownMenu<*>>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        items(items = menus) { menu ->
            val selectedOption = menu.selectedOption.value
            val text = when (selectedOption) {
                is RoomCleaningStatus -> { stringResource(selectedOption.title) }
                is UserUiItem -> { selectedOption.name }
                else -> { " " }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(0.6f),
                    text = stringResource(id = menu.label),
                    fontSize = 18.sp
                )
                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            menu.toggleExpanded()
                        },
                    horizontalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(4.dp),
                        text = text,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )

                    VerticalDivider(
                        modifier = Modifier,
                        color = Color.Black,
                        thickness = 2.dp
                    )

                    Icon(
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .aspectRatio(1f),
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                    DropdownMenu(
                        modifier = Modifier,
                        expanded = menu.isExpanded.value,
                        onDismissRequest = { menu.isExpanded.value = false }
                    ) {
                        when (menu) {
                            is DropDownMenu.StatusMenu -> {
                                menu.options.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = stringResource(id = option.title),
                                                fontSize = 20.sp
                                            )
                                        },
                                        onClick = {
                                            menu.updatedSelectedStatus(option)
                                        }
                                    )
                                }
                            }

                            is DropDownMenu.UserMenu -> {
                                menu.options.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = option.name,
                                                fontSize = 20.sp
                                            )
                                        },
                                        onClick = {
                                            menu.updatedSelectedUser(option)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun DialogFormButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(intrinsicSize = IntrinsicSize.Min)
            .padding(all = 4.dp),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            fontSize = 15.sp
        )
    }
}