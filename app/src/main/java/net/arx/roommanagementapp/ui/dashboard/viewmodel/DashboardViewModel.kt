package net.arx.roommanagementapp.ui.dashboard.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.R
import net.arx.roommanagementapp.framework.db.entity.UserRole
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.model.DashboardUiState
import net.arx.roommanagementapp.ui.user.mapper.UserUiMapper
import net.arx.roommanagementapp.ui.user.model.UserUiItem
import net.arx.roommanagementapp.usecase.user.GetUserUseCase
import net.arx.roommanagementapp.usecase.user.InsertUserUseCase
import net.arx.roommanagementapp.usecase.user.LoginUserUseCase
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val insertUserUseCase: InsertUserUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        DashboardUiState(
            onBackButtonClicked = { onBackButtonClicked() },
            openAdminPinForm = { onOpenAdminPinForm() },
            onPinDialogDismiss = { onLoginDialogDismiss() },
            onPinComplete = { onPinComplete() },
            onNavigateToRoom = { onNavigateToRoom(it) }
        )
    )
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        launch {
            val userEntity = getUserUseCase(id = 1)
            if(userEntity == null) {
                insertUserUseCase(
                    username = ADMIN_USERNAME,
                    password = ADMIN_PASSWORD,
                    userRole = UserRole.ADMIN
                )
            }
        }
    }

    private fun onBackButtonClicked() {
        if(_uiState.value.isAdmin.value) {
            _uiState.value.isAdmin.value = false
        } else {
            _uiState.value.backstackEntries.removeLastOrNull()
        }
    }
    private fun onOpenAdminPinForm() {
        if(!_uiState.value.isAdmin.value){
            openLoginDialog()
        }
    }

    private fun openLoginDialog() {
        _uiState.value.openPinDialog.value = true
    }

    private fun onLoginDialogDismiss() {
        _uiState.value.pinFormUiItem.clear()
        _uiState.value.openPinDialog.value = false
    }

    private fun onPinComplete() {
        launch {
            val passwordInput = _uiState.value.pinFormUiItem.text.value
            val user = loginUserUseCase(password = passwordInput)

            when{
                user?.role == UserRole.ADMIN -> {
                    _uiState.value.isAdmin.value = true
                    onNavigateToLobby()
                    onLoginDialogDismiss()
                }
                user == null -> {
                    _uiState.value.pinFormUiItem.isError.value = true
                }
                else -> {}
            }
            _uiState.value.pinFormUiItem.clear()
        }
    }

    private fun onNavigateToRoom(roomId: Long) {
        val isAdmin = _uiState.value.isAdmin.value
        _uiState.value.selectedRoomId.value = roomId
        if(isAdmin) {
            onNavigateToAdminRoom()
        } else {
            onNavigateToUserRoom()
        }
    }

    private fun onNavigateToLobby() {
        _uiState.value.backstackEntries.add(DashboardNavEntries.Lobby)
    }

    private fun onNavigateToUserRoom() {
        _uiState.value.backstackEntries.add(DashboardNavEntries.UserRoom)
    }

    private fun onNavigateToAdminRoom() {
        _uiState.value.backstackEntries.add(DashboardNavEntries.AdminRoom)
    }

    companion object {
        const val ADMIN_USERNAME = "Admin"
        const val ADMIN_PASSWORD = "0000"
    }

}