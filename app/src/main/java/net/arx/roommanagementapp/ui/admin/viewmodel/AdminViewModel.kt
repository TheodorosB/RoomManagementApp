package net.arx.roommanagementapp.ui.admin.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.admin.model.FieldUiItem
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.cleaner.mapper.CleanerUiMapper
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.usecase.room.GetAllRoomsUseCase
import net.arx.roommanagementapp.usecase.room.InsertRoomUseCase
import net.arx.roommanagementapp.usecase.user.GetAllCleanersUseCase
import net.arx.roommanagementapp.usecase.user.InsertUserUseCase
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val cleanerUiMapper: CleanerUiMapper,
    private val roomUiMapper: RoomUiMapper,
    private val insertRoomUseCase: InsertRoomUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getAllCleanersUseCase: GetAllCleanersUseCase
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
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        launch {
            refreshCleaners()
            refreshRooms()
        }
    }

    private fun onCleanerClicked() {

    }

    private fun onAddNewCleanerClicked() {
        _uiState.value.formUiItem.value = DialogFormUiItem.Cleaner()
        _uiState.value.formUiItem.value.title.value = R.string.add_cleaner_title
        _uiState.value.openDialogForm.value = true
    }

    private fun onSubmitFormClicked() {
        val formUiItem = _uiState.value.formUiItem.value
        when(formUiItem) {
            is DialogFormUiItem.Cleaner -> {
                launch {
                    insertUserUseCase(
                        username = formUiItem.fields.firstOrNull { it is FieldUiItem.UsernameField }?.text?.value,
                        password = formUiItem.fields.firstOrNull { it is FieldUiItem.PasswordField }?.text?.value
                    )
                    refreshCleaners()
                }
            }
            is DialogFormUiItem.Room -> {
                launch {
                    insertRoomUseCase(roomName = formUiItem.fields.firstOrNull { it is FieldUiItem.RoomField }?.text?.value)
                    refreshRooms()
                }
            }
        }
        _uiState.value.formUiItem.value.resetForm()
        closeDialogForm()
    }

    private fun onAddNewRoomClicked() {
        _uiState.value.formUiItem.value = DialogFormUiItem.Room()
        _uiState.value.formUiItem.value.title.value = R.string.add_room_title
        _uiState.value.openDialogForm.value = true
    }

    private fun closeDialogForm() {
        _uiState.value.formUiItem.value.resetForm()
        _uiState.value.openDialogForm.value = false
    }

    private suspend fun refreshRooms() {
        val roomEntities = getAllRoomsUseCase()
        _uiState.value.rooms.clear()
        _uiState.value.rooms.addAll(roomUiMapper(roomEntities, isAdmin = true))
    }

    private suspend fun refreshCleaners() {
        val userEntities = getAllCleanersUseCase()
        _uiState.value.cleaners.clear()
        _uiState.value.cleaners.addAll(cleanerUiMapper(userEntities))
    }

}