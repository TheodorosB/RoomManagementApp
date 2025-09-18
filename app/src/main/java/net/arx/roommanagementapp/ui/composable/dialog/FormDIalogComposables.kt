package net.arx.roommanagementapp.ui.composable.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.lobby.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.lobby.model.FieldUiItem
import net.arx.roommanagementapp.ui.theme.SpacingCustom_10dp
import net.arx.roommanagementapp.ui.theme.SpacingHalf_8dp
import net.arx.roommanagementapp.ui.theme.SpacingQuarter_4dp

@Composable
internal fun FormDialog(
    formUiItem: DialogFormUiItem,
    onValidateText: (FieldUiItem) -> Unit,
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
                    style = MaterialTheme.typography.bodySmall
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
                    .clip(shape = RoundedCornerShape(SpacingCustom_10dp))
                    .padding(horizontal = SpacingHalf_8dp, vertical = SpacingQuarter_4dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.CenterVertically)
            ) {
                FormDialogFields(
                    fields = formUiItem.fields,
                    onValidateText = onValidateText
                )
            }
        },
        dismissButton = {
            DialogFormButton(
                text = stringResource(R.string.form_dialog_cancel_button),
                onClick = onDismissRequest
            )
        },
        confirmButton = {
            DialogFormButton(
                text = stringResource(R.string.form_dialog_confirm_button),
                onClick = onAddCleanerClicked,
                isEnabled = !formUiItem.hasError
            )
        }
    )
}

@Composable
private fun FormDialogFields(
    fields: List<FieldUiItem>,
    onValidateText: (FieldUiItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = SpacingHalf_8dp, alignment = Alignment.CenterVertically)
    ) {
        items(items = fields) { field ->
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                value = field.text.value,
                onValueChange = {
                    field.updateText(it)
                    onValidateText(field)
                },
                label = {
                    Text(
                        text = stringResource(field.label),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                isError = field.alreadyExists.value,
                textStyle = MaterialTheme.typography.titleMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = field.keyboardType,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                )
            )
        }
    }
}

@Composable
internal fun DialogFormButton(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(intrinsicSize = IntrinsicSize.Min)
            .padding(all = SpacingQuarter_4dp),
        onClick = {
            onClick()
        },
        enabled = isEnabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}