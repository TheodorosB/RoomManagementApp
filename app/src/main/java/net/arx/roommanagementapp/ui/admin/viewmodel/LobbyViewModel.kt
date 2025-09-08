package net.arx.roommanagementapp.ui.admin.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.admin.model.FieldUiItem
import net.arx.roommanagementapp.ui.admin.model.LobbyUiState
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.ui.user.mapper.UserUiMapper
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.usecase.room.GetAllRoomsUseCase
import net.arx.roommanagementapp.usecase.room.InsertRoomUseCase
import net.arx.roommanagementapp.usecase.status.GetRoomStatusesUseCase
import net.arx.roommanagementapp.usecase.status.InsertRoomStatusUseCase
import net.arx.roommanagementapp.usecase.user.GetAllUsersUseCase
import net.arx.roommanagementapp.usecase.user.InsertUserUseCase
import javax.inject.Inject
import kotlin.properties.Delegates


@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val userUiMapper: UserUiMapper,
    private val roomUiMapper: RoomUiMapper,
    private val insertRoomUseCase: InsertRoomUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val insertRoomStatusUseCase: InsertRoomStatusUseCase,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getRoomStatusesUseCase: GetRoomStatusesUseCase,

    ): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        LobbyUiState(
            onUserClicked = { onUserClicked() },
            onAddNewUserClicked = { onAddNewUserClicked() },
            onAddNewRoomClicked = { onAddNewRoomClicked() },
            onCloseDialogForm = { closeDialogForm() },
            onSubmitFormClicked = { onSubmitFormClicked() }
        )
    )
    val uiState: StateFlow<LobbyUiState> = _uiState.asStateFlow()

    var fetchDataByUser by Delegates.observable(_uiState.value.loggedInUser.value){ property, oldValue, newValue ->
        if(newValue.id != null) {
            init()
        }
    }

    fun init() {
        launch {
            refreshUsers()
            refreshRooms()
        }
    }

    fun updateDate(date: DateUiItem) {
        _uiState.value.date.value = date
        if(_uiState.value.loggedInUser.value.id != null) {
            launch {
                refreshRooms()
            }
        }
    }

    fun updateUser(user: UserUiItem) {
        _uiState.value.loggedInUser.value = user
        fetchDataByUser = user
    }

    private fun onUserClicked() {
        //TODO Mark Specific User Rooms ??
    }

    private fun onAddNewUserClicked() {
        _uiState.value.formUiItem.value = DialogFormUiItem.User()
        _uiState.value.formUiItem.value.title.value = R.string.add_cleaner_title
        _uiState.value.openDialogForm.value = true
    }

    private fun onSubmitFormClicked() {
        val formUiItem = _uiState.value.formUiItem.value
        when(formUiItem) {
            is DialogFormUiItem.User -> {
                launch {
                    insertUserUseCase(
                        username = formUiItem.fields.firstOrNull { it is FieldUiItem.UsernameField }?.text?.value,
                        password = formUiItem.fields.firstOrNull { it is FieldUiItem.PasswordField }?.text?.value
                    )
                    refreshUsers()
                }
            }
            is DialogFormUiItem.Room -> {
                launch {
                    insertRoomUseCase(roomName = formUiItem.fields.firstOrNull { it is FieldUiItem.RoomField }?.text?.value)
                    refreshRooms()
                }
            }
            /*is DialogFormUiItem.Task -> {
                launch {
                    val user = formUiItem.dropDownMenus.firstOrNull { it is DropDownMenu.UserMenu }?.selectedOption?.value
                    val status = formUiItem.dropDownMenus.firstOrNull { it is DropDownMenu.StatusMenu }?.selectedOption?.value

                    if(user == null || status == null) return@launch

                    val task = taskUiMapper(
                        user = user as UserUiItem,
                        status = status,
                        date = _uiState.value.date.value,
                        roomId = formUiItem.roomId,
                    )
                    insertRoomStatusUseCase(task = task)
                    refreshRooms()
                }
            }*/
        }
        _uiState.value.formUiItem.value.resetForm()
        closeDialogForm()
    }

    /*private fun onRoomClicked(id : Long) {
        val users = _uiState.value.users
        _uiState.value.formUiItem.value = DialogFormUiItem.Task(roomId = id)
        _uiState.value.formUiItem.value.title.value = R.string.dialog_form_room_edit_title
        _uiState.value.formUiItem.value.dropDownMenus.filterIsInstance<DropDownMenu.StatusMenu>().firstOrNull().apply {
            this?.selectedOption?.value = _uiState.value.rooms.firstOrNull { it.id == id }?.status?.value ?: RoomCleaningStatus.General()
        }
        _uiState.value.formUiItem.value.dropDownMenus.filterIsInstance<DropDownMenu.UserMenu>().firstOrNull().apply {
            this?.options
                ?.apply {
                    clear()
                    addAll(users)
                }
            this?.selectedOption?.value = _uiState.value.rooms.firstOrNull { it.id == id }?.user ?: UserUiItem()
        }
        _uiState.value.openDialogForm.value = true
    }*/

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
        val roomStatuses = getRoomStatusesUseCase(
            dayStart = _uiState.value.date.value.dayStart.value,
            dayEnd = _uiState.value.date.value.dayEnd.value
        )
        _uiState.value.rooms.clear()
        _uiState.value.rooms.addAll(
            roomUiMapper(
                roomEntities = roomEntities,
                statuses = roomStatuses,
                users = _uiState.value.users,
                isAdmin = true
            )
        )
    }

    private suspend fun refreshUsers() {
        val userEntities = getAllUsersUseCase()
        val users = userUiMapper(userEntities)
        _uiState.value.users.clear()
        _uiState.value.users.addAll(users)
    }

}