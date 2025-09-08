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
import net.arx.roommanagementapp.usecase.user.LoginUserUseCase
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userUiMapper: UserUiMapper,
    private val loginUserUseCase: LoginUserUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        DashboardUiState(
            onBackButtonClicked = { onBackButtonClicked() },
            openAdminPinForm = { onOpenAdminPinForm() },
            openUserPinForm = { onOpenUserPinForm() },
            onPinDialogDismiss = { onLoginDialogDismiss() },
            onPinComplete = { onPinComplete() },
            onNavigateToLobby = { onNavigateToLobby() }
        )
    )
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private fun onBackButtonClicked() {
        _uiState.value.backstackEntries.removeLastOrNull()
    }
    private fun onOpenAdminPinForm() {
        if(!_uiState.value.loggedInUser.value.isAdmin){
            _uiState.value.pinFormUiItem.title.value = R.string.pin_dialog_title_admin
            openLoginDialog()
        }
    }

    private fun onOpenUserPinForm() {
        _uiState.value.pinFormUiItem.title.value = R.string.pin_dialog_title_user
        openLoginDialog()
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
                user?.role == UserRole.ADMIN && !_uiState.value.loggedInUser.value.isAdmin -> {
                    _uiState.value.loggedInUser.value = userUiMapper(userEntity = user)
                    onNavigateToLobby()
                    onLoginDialogDismiss()
                }
                user?.role == UserRole.USER && _uiState.value.loggedInUser.value.id != user.id -> {
                    _uiState.value.loggedInUser.value = userUiMapper(userEntity = user)
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

    private fun onNavigateToUser() {
        _uiState.value.backstackEntries.add(DashboardNavEntries.User)
    }

    private fun onNavigateToLobby() {
        _uiState.value.backstackEntries.add(DashboardNavEntries.Lobby)
    }

}