package net.arx.roommanagementapp.ui.composable.dialog

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.dashboard.model.PinFormUiItem
import net.arx.roommanagementapp.ui.theme.SpacingCustom_12dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_14dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_24dp
import net.arx.roommanagementapp.ui.theme.SpacingCustom_60dp
import net.arx.roommanagementapp.ui.theme.SpacingDefault_16dp
import net.arx.roommanagementapp.ui.theme.SpacingEighth_2dp
import net.arx.roommanagementapp.ui.theme.SpacingSingle_1dp

@Composable
internal fun PinDialog(
    pinForm: PinFormUiItem,
    onPasswordComplete: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    PinDialogContent(
        pinForm = pinForm,
        onDismissRequest = onDismissRequest,
        onPasswordComplete = onPasswordComplete
    )
}
@Composable
private fun PinDialogContent(
    pinForm: PinFormUiItem,
    onPasswordComplete: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = pinForm.title),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Icon(
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .aspectRatio(1f)
                        .clickable { onDismissRequest() },
                    painter = painterResource(R.drawable.ic_room_management_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = SpacingDefault_16dp, alignment = Alignment.CenterVertically)) {

                PinField(
                    pinForm = pinForm,
                    onPasswordComplete = onPasswordComplete
                )
                if (pinForm.isError.value) {
                    Text(
                        text = stringResource(R.string.pin_dialog_wrong_password),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Red
                    )
                }
            }
        },
        dismissButton = {
            DialogFormButton(
                text = stringResource(R.string.form_dialog_cancel_button),
                onClick = onDismissRequest
            )
        },
        confirmButton = {}
    )
}

@Composable
private fun PinField(
    pinForm: PinFormUiItem,
    modifier: Modifier = Modifier,
    onPasswordComplete: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = pinForm.text.value,
        onValueChange = { raw ->
            pinForm.update(raw)
            if (pinForm.isComplete) {
                onPasswordComplete()
            }
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .size(SpacingSingle_1dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        cursorBrush = SolidColor(Color.Transparent)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = SpacingCustom_14dp, alignment = Alignment.CenterHorizontally)
    ) {
        pinForm.pinCode.forEachIndexed { index, item ->
            val showCursor = isFocused && pinForm.text.value.length == index

            Box(
                modifier = Modifier
                    .size(SpacingCustom_60dp)
                    .border(
                        width = SpacingEighth_2dp,
                        color = if(pinForm.isError.value) Color.Red else MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(SpacingCustom_12dp)
                    )
                    .clip(shape = RoundedCornerShape(SpacingCustom_12dp))
                    .background(color = MaterialTheme.colorScheme.background)
                    .clickable { focusRequester.requestFocus() },
                contentAlignment = Alignment.Center
            ) {
                when {
                    item.isFilled -> {
                        Text(
                            text = stringResource(id = R.string.pin_filled_digit),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    showCursor -> {
                        BlinkingCursor()
                    }
                }
            }
        }
    }
}

@Composable
private fun BlinkingCursor() {
    val alpha by rememberInfiniteTransition(label = "")
        .animateFloat(
            initialValue = 0.2f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    Box(
        Modifier
            .width(SpacingEighth_2dp)
            .height(SpacingCustom_24dp)
            .alpha(alpha)
            .background(MaterialTheme.colorScheme.primary)
    )
}