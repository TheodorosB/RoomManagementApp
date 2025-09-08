package net.arx.roommanagementapp.ui.user.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DateUiItem
import net.arx.roommanagementapp.ui.room.mapper.RoomUiMapper
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.ui.user.model.RoomTasksUiState
import net.arx.roommanagementapp.usecase.room.GetRoomUseCase
import net.arx.roommanagementapp.usecase.status.GetRoomStatusUseCase
import javax.inject.Inject

@HiltViewModel
class RoomTasksViewModel @Inject constructor(
    private val roomUiMapper: RoomUiMapper,
    private val getRoomUseCase: GetRoomUseCase,
    private val getRoomStatusUseCase: GetRoomStatusUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow(RoomTasksUiState())
    val uiState: StateFlow<RoomTasksUiState> = _uiState.asStateFlow()

    fun loadUserData(
        roomId: Long?,
        user: UserUiItem,
        date: DateUiItem
    ) {
        _uiState.value.user.value = user
        if(roomId != null) {
            launch {
                val roomEntity = getRoomUseCase(id = roomId)
                val roomStatusEntity = getRoomStatusUseCase(
                    id = roomId,
                    dayStart = date.dayStart.value,
                    dayEnd = date.dayEnd.value
                )
                roomStatusEntity?.let {
                    val room = roomUiMapper(
                        roomEntity = roomEntity,
                        statusEntity = it,
                        isAdmin = user.isAdmin
                    )
                    _uiState.value.room.value = room
                }
            }
        }
    }
}