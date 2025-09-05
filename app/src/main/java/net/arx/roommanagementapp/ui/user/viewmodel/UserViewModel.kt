package net.arx.roommanagementapp.ui.user.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.ui.user.model.UserUiState
import net.arx.roommanagementapp.usecase.room.GetAllRoomsUseCase
import net.arx.roommanagementapp.usecase.task.GetTasksByUserUseCase
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val roomUiMapper: RoomUiMapper,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getTasksByUserUseCase: GetTasksByUserUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadUserData(user: UserUiItem, date: DateUiItem) {
        _uiState.value.rooms.clear()
        _uiState.value.user.value = user

        launch {
            val roomEntities = getAllRoomsUseCase()
            val userTasks = getTasksByUserUseCase(
                id = user.id,
                dayStart = date.dayStart.value,
                dayEnd = date.dayEnd.value
            )
            val rooms = roomUiMapper.mapRoomsByTasks(
                roomEntities = roomEntities,
                tasks = userTasks,
                isAdmin = false
            )
            _uiState.value.rooms.addAll(rooms)
        }
    }
}