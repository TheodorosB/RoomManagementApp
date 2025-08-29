package net.arx.roommanagementapp.ui.admin.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.cleaner.mapper.CleanerUiMapper
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val cleanerUiMapper: CleanerUiMapper,
    private val roomUiMapper: RoomUiMapper
): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        AdminUiState(
            onSubmitFormClicked = { onSubmitFormClicked() },
            onCleanerClicked = { onCleanerClicked() },
            onCloseAlertDialog = { closeDialogForm() },
            onAddNewCleanerClicked = { onAddNewCleanerClicked() },
            onAddNewRoomClicked = { onAddNewRoomClicked() }
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value.cleaners.addAll(cleanerUiMapper())
    }

    private fun onCleanerClicked() {

    }

    private fun onAddNewCleanerClicked() {
        _uiState.value.formUiItem.value = DialogFormUiItem.Cleaner()
        _uiState.value.openDialogForm.value = true
    }

    private fun onSubmitFormClicked() {
        val formUiItem = _uiState.value.formUiItem.value
        when(formUiItem) {
            is DialogFormUiItem.Cleaner -> {
                _uiState.value.cleaners.add(
                    CleanerUiItem(name = formUiItem.fields.first().text.value)
                )
            }
            is DialogFormUiItem.Room -> {
                _uiState.value.rooms.add(
                    roomUiMapper(
                        name = formUiItem.fields.first().text.value,
                        isAdmin = true
                    )
                )
            }
        }
        _uiState.value.formUiItem.value.resetForm()
        closeDialogForm()
    }

    private fun onAddNewRoomClicked() {
        _uiState.value.formUiItem.value = DialogFormUiItem.Room()
        _uiState.value.openDialogForm.value = true
    }

    private fun closeDialogForm() {
        _uiState.value.formUiItem.value.resetForm()
        _uiState.value.openDialogForm.value = false
    }

}