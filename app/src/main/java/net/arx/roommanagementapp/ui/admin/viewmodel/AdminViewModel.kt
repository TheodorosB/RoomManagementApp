package net.arx.roommanagementapp.ui.admin.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.admin.model.DialogFormUiItem
import net.arx.roommanagementapp.ui.admin.model.DropDownMenu
import net.arx.roommanagementapp.ui.admin.model.FieldUiItem
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.ui.room.model.RoomCleaningStatus
import net.arx.roommanagementapp.ui.task.mapper.TaskUiMapper
import net.arx.roommanagementapp.ui.user.mapper.UserUiMapper
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.usecase.room.GetAllRoomsUseCase
import net.arx.roommanagementapp.usecase.room.InsertRoomUseCase
import net.arx.roommanagementapp.usecase.task.GetTasksByDateUseCase
import net.arx.roommanagementapp.usecase.task.InsertTaskUseCase
import net.arx.roommanagementapp.usecase.user.GetAllUsersUseCase
import net.arx.roommanagementapp.usecase.user.InsertUserUseCase
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val userUiMapper: UserUiMapper,
    private val roomUiMapper: RoomUiMapper,
    private val taskUiMapper: TaskUiMapper,
    private val insertRoomUseCase: InsertRoomUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getTasksByDateUseCase: GetTasksByDateUseCase,

    ): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        AdminUiState(
            onUserClicked = { onUserClicked() },
            onAddNewUserClicked = { onAddNewUserClicked() },
            onRoomClicked = { onRoomClicked(it) },
            onAddNewRoomClicked = { onAddNewRoomClicked() },
            onCloseDialogForm = { closeDialogForm() },
            onSubmitFormClicked = { onSubmitFormClicked() }
        )
    )
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        launch {
            refreshUsers()
        }
    }

    fun updateDate(date: DateUiItem) {
        _uiState.value.date.value = date
        launch {
            refreshRooms()
        }
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
            is DialogFormUiItem.Task -> {
                launch {
                    val user = formUiItem.dropDownMenus.firstOrNull { it is DropDownMenu.UserMenu }?.selectedOption?.value
                    val status = formUiItem.dropDownMenus.firstOrNull { it is DropDownMenu.StatusMenu }?.selectedOption?.value

                    if(user == null || status == null) return@launch

                    val task = taskUiMapper(
                        user = user as UserUiItem,
                        status = status as RoomCleaningStatus,
                        date = _uiState.value.date.value,
                        roomId = formUiItem.roomId,
                    )
                    insertTaskUseCase(task = task,)
                    refreshRooms()
                }
            }
        }
        _uiState.value.formUiItem.value.resetForm()
        closeDialogForm()
    }

    private fun onRoomClicked(id : Long) {
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
        val taskEntities = getTasksByDateUseCase(
            dayStart = _uiState.value.date.value.dayStart.value,
            dayEnd = _uiState.value.date.value.dayEnd.value
        )
        _uiState.value.rooms.clear()
        _uiState.value.rooms.addAll(
            roomUiMapper(
                roomEntities = roomEntities,
                tasks = taskEntities,
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