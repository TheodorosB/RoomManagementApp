package net.arx.roommanagementapp.ui.room.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.ui.room.model.RoomManagementUiState
import net.arx.roommanagementapp.ui.user.mapper.UserUiMapper
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.usecase.room.GetRoomUseCase
import net.arx.roommanagementapp.usecase.status.GetRoomStatusUseCase
import net.arx.roommanagementapp.usecase.status.InsertRoomStatusUseCase
import net.arx.roommanagementapp.usecase.user.GetAllUsersUseCase
import javax.inject.Inject

@HiltViewModel
class RoomManagementViewModel @Inject constructor(
    private val userUiMapper: UserUiMapper,
    private val roomUiMapper: RoomUiMapper,
    private val getRoomUseCase: GetRoomUseCase,
    private val insertRoomStatusUseCase: InsertRoomStatusUseCase,
    private val getRoomStatusUseCase: GetRoomStatusUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        RoomManagementUiState(
            onUserClicked = { onUserClicked(id = it) },
            onUpdateStatus = { updateRoomStatus() }
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateRoomDetails(roomId: Long?, date: DateUiItem) {
        if(roomId != null) {
            _uiState.value.date.value = date
            launch {
                refreshUsers()
                val roomEntity = getRoomUseCase(id = roomId)
                val statusEntity = getRoomStatusUseCase(
                    id = roomId,
                    dayStart = date.dayStart.value,
                    dayEnd = date.dayEnd.value
                )
                val room = roomUiMapper(
                    roomEntity = roomEntity,
                    statusEntity = statusEntity,
                    isAdmin = false
                )
                _uiState.value.users.firstOrNull { it.id == statusEntity?.userId }?.isSelected?.value = true
                _uiState.value.room.value = room
            }
        }
    }

    private suspend fun refreshUsers() {
        val userEntities = getAllUsersUseCase()
        val users = userUiMapper(
            users = userEntities,
            isClickable = true
        )
        _uiState.value.users.clear()
        _uiState.value.users.addAll(users)
    }

    private fun onUserClicked(id: Long?) {
        _uiState.value.users.forEach { user ->
            user.isSelected.value = user.id == id && !user.isSelected.value
        }
        updateRoomStatus()
    }

    private fun updateRoomStatus() {
        val date = _uiState.value.date.value
        val selectedRoom = _uiState.value.room.value
        val selectedUser = _uiState.value.users.firstOrNull { it.isSelected.value } ?: UserUiItem()
        launch {
            insertRoomStatusUseCase(
                userId = selectedUser.id,
                roomId = selectedRoom.id,
                dayStart = date.dayStart.value,
                dayEnd = date.dayEnd.value,
                tasks = selectedRoom.tasks
            )
        }
    }
}