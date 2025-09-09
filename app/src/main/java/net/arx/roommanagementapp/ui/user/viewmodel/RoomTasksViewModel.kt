package net.arx.roommanagementapp.ui.user.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.ui.user.mapper.UserUiMapper
import net.arx.roommanagementapp.ui.user.model.RoomTasksUiState
import net.arx.roommanagementapp.usecase.room.GetRoomUseCase
import net.arx.roommanagementapp.usecase.status.GetRoomStatusUseCase
import net.arx.roommanagementapp.usecase.status.InsertRoomStatusUseCase
import net.arx.roommanagementapp.usecase.user.GetUserUseCase
import javax.inject.Inject

@HiltViewModel
class RoomTasksViewModel @Inject constructor(
    private val roomUiMapper: RoomUiMapper,
    private val userUiMapper: UserUiMapper,
    private val getUserUseCase: GetUserUseCase,
    private val getRoomUseCase: GetRoomUseCase,
    private val insertRoomStatusUseCase: InsertRoomStatusUseCase,
    private val getRoomStatusUseCase: GetRoomStatusUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow(RoomTasksUiState(
        onUpdateStatus = { updateRoomStatus() }
    ))
    val uiState: StateFlow<RoomTasksUiState> = _uiState.asStateFlow()

    fun loadUserData(
        roomId: Long?,
        date: DateUiItem
    ) {
        _uiState.value.date.value = date
        roomId?.let {
            launch {
                fetchRoomDetails(
                    roomId = it,
                    date = date
                )
                fetchUser()
            }
        }
    }

    private suspend fun fetchRoomDetails(
        roomId: Long,
        date: DateUiItem
    ) {
        val roomEntity = getRoomUseCase(id = roomId)
        val roomStatusEntity = getRoomStatusUseCase(
            id = roomId,
            dayStart = date.dayStart.value,
            dayEnd = date.dayEnd.value
        )
        val room = roomUiMapper(
            roomEntity = roomEntity,
            statusEntity = roomStatusEntity,
            isAdmin = false
        )
        _uiState.value.room.value = room
    }

    private suspend fun fetchUser() {
        val userId = _uiState.value.room.value.user.id
        userId?.let {
            val userEntity = getUserUseCase(id = it)
            val user = userUiMapper(userEntity = userEntity)
            _uiState.value.user.value = user
        }
    }

    private fun updateRoomStatus() {
        val date = _uiState.value.date.value
        val selectedRoom = _uiState.value.room.value
        val selectedUser = _uiState.value.user.value

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