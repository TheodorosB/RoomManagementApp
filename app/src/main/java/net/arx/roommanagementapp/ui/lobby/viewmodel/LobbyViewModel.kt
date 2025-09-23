package net.arx.roommanagementapp.ui.lobby.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.lobby.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.lobby.model.FieldUiItem
import net.arx.roommanagementapp.ui.lobby.model.LobbyUiState
import net.arx.roommanagementapp.ui.core.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.ui.room.model.RoomUiItem
import net.arx.roommanagementapp.ui.user.mapper.UserUiMapper
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.usecase.room.DeleteRoomUseCase
import net.arx.roommanagementapp.usecase.room.GetAllRoomsUseCase
import net.arx.roommanagementapp.usecase.room.InsertRoomUseCase
import net.arx.roommanagementapp.usecase.room.RoomExistsUseCase
import net.arx.roommanagementapp.usecase.status.GetRoomStatusesUseCase
import net.arx.roommanagementapp.usecase.user.DeleteUserUseCase
import net.arx.roommanagementapp.usecase.user.GetAllUsersUseCase
import net.arx.roommanagementapp.usecase.user.InsertUserUseCase
import net.arx.roommanagementapp.usecase.user.UsernameExistsUseCase
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val userUiMapper: UserUiMapper,
    private val roomUiMapper: RoomUiMapper,
    private val insertRoomUseCase: InsertRoomUseCase,
    private val roomExistsUseCase: RoomExistsUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val usernameExistsUseCase: UsernameExistsUseCase,
    private val getRoomStatusesUseCase: GetRoomStatusesUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        LobbyUiState(
            onDeleteUserClicked = { onDeleteUserClicked(it) },
            onAddNewUserClicked = { onAddNewUserClicked() },
            onDeleteRoomClicked = { onDeleteRoomClicked(it) },
            onAddNewRoomClicked = { onAddNewRoomClicked() },
            onCloseDialogForm = { closeDialogForm() },
            onSubmitFormClicked = { onSubmitFormClicked() },
            onValidateText = { onValidateText(it) },
        )
    )
    val uiState: StateFlow<LobbyUiState> = _uiState.asStateFlow()

    var fetchData by Delegates.observable(_uiState.value.isAdmin.value){ property, oldValue, newValue ->
        init()
    }

    fun init() {
        launch {
            refreshUsers()
            refreshRooms()
        }
    }

    fun refreshData(date: DateUiItem, isAdmin: Boolean) {
        _uiState.value.date.value = date
        _uiState.value.isAdmin.value = isAdmin
        fetchData = isAdmin
    }

    private fun onDeleteUserClicked(user: UserUiItem) {
        user.id?.let {
            _uiState.value.selectedUser.value = user
            _uiState.value.formUiItem.value = DialogFormUiItem.DeleteUserForm()
            _uiState.value.formUiItem.value.descriptionParam.value = user.name
            openDialogForm()
        }
    }

    private fun onAddNewUserClicked() {
        _uiState.value.formUiItem.value = DialogFormUiItem.UserForm()
        openDialogForm()
    }

    private fun onDeleteRoomClicked(room: RoomUiItem) {
        _uiState.value.selectedRoom.value = room
        _uiState.value.formUiItem.value = DialogFormUiItem.DeleteRoomForm()
        _uiState.value.formUiItem.value.descriptionParam.value = room.name
        openDialogForm()
    }

    private fun onAddNewRoomClicked() {
        _uiState.value.formUiItem.value = DialogFormUiItem.RoomForm()
        openDialogForm()
    }

    private fun onValidateText(field: FieldUiItem) {
        val text = field.text.value
        launch {
            when (field) {
                is FieldUiItem.RoomField -> {
                    val alreadyExists = roomExistsUseCase(name = text)
                    _uiState.value.formUiItem.value.fields.firstOrNull { it is FieldUiItem.RoomField }?.alreadyExists?.value = alreadyExists
                }

                is FieldUiItem.UsernameField -> {
                    val alreadyExists = usernameExistsUseCase(username = text)
                    _uiState.value.formUiItem.value.fields.firstOrNull { it is FieldUiItem.UsernameField }?.alreadyExists?.value = alreadyExists
                }
            }
        }
    }

    private fun onSubmitFormClicked() {
        val form = _uiState.value.formUiItem.value
        when(form) {
            is DialogFormUiItem.DeleteUserForm -> {
                deleteUser()
            }
            is DialogFormUiItem.UserForm -> {
                insertUser(form = form)
            }
            is DialogFormUiItem.DeleteRoomForm -> {
                deleteRoom()
            }
            is DialogFormUiItem.RoomForm -> {
                insertRoom(form = form)
            }
        }
        _uiState.value.formUiItem.value.resetForm()
        closeDialogForm()
    }

    private fun deleteUser() {
        launch {
            val id = _uiState.value.selectedUser.value?.id
            id?.let {
                deleteUserUseCase(id = id)
                fetchData = _uiState.value.isAdmin.value
            }
            _uiState.value.selectedUser.value = null
        }
    }
    private fun insertUser(form: DialogFormUiItem) {
        launch {
            insertUserUseCase(username = form.fields.firstOrNull { it is FieldUiItem.UsernameField }?.text?.value)
            fetchData = _uiState.value.isAdmin.value
        }
    }
    private fun deleteRoom() {
        launch {
            val id = _uiState.value.selectedRoom.value?.id
            id?.let {
                deleteRoomUseCase(id = id)
                fetchData = _uiState.value.isAdmin.value
            }
            _uiState.value.selectedRoom.value = null
        }
    }
    private fun insertRoom(form: DialogFormUiItem) {
        launch {
            insertRoomUseCase(roomName = form.fields.firstOrNull { it is FieldUiItem.RoomField }?.text?.value)
            fetchData = _uiState.value.isAdmin.value
        }
    }

    private fun openDialogForm() {
        _uiState.value.openDialogForm.value = true
    }

    private fun closeDialogForm() {
        _uiState.value.formUiItem.value.resetForm()
        _uiState.value.openDialogForm.value = false
    }

    private suspend fun refreshRooms() {
        val roomEntities = getAllRoomsUseCase()
        val roomStatuses = getRoomStatusesUseCase(
            dayStart = _uiState.value.date.value.dayStart.value,
            dayEnd = _uiState.value.date.value.dayEnd.value
        )
        val isAdmin = _uiState.value.isAdmin.value
        _uiState.value.rooms.clear()
        _uiState.value.rooms.addAll(
            roomUiMapper(
                roomEntities = roomEntities,
                statuses = roomStatuses,
                users = _uiState.value.users,
                isAdmin = isAdmin,
                hasStatus = true
            )
        )
        val assignedUsers = _uiState.value.rooms.map { it.user.id }
        _uiState.value.users.forEach { it.isSelected.value = it.id in assignedUsers }
    }

    private suspend fun refreshUsers() {
        val userEntities = getAllUsersUseCase()
        val users = userUiMapper(users = userEntities)
        _uiState.value.users.clear()
        _uiState.value.users.addAll(users)
    }
}