package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem

@Composable
fun FormDialog(
    formUiItem: DialogFormUiItem,
    onDismissRequest: () -> Unit,
    onAddCleanerClicked: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(0.8f),
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    items(formUiItem.fields) { field ->
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
fun DialogFormButton(
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