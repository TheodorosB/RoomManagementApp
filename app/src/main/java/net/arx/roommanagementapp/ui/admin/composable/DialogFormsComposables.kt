package net.arx.roommanagementapp.ui.admin.composable

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.CenterVertically)
            ) {
                items(formUiItem.fields) { field ->
                    TextField(
                        value = field.text.value,
                        onValueChange = { field.onUpdateName(it) },
                        label = {
                            Text(
                                text = stringResource(field.label),
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                DialogFormButton(
                    text = stringResource(R.string.dialog_form_cancel_button),
                    onClick = onDismissRequest
                )

                DialogFormButton(
                    text = stringResource(R.string.dialog_form_confirm_button),
                    onClick = onAddCleanerClicked
                )
            }
        }
    }
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