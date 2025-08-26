package net.arx.roommanagementapp.ui.admin.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        AdminUiState(
            onPinPanelIconClicked = { onPinPanelIconClicked() },
            onUserIconClicked = { onUserIconClicked() },
            onHomeScreenClicked = { onHomeScreenClicked() }
        )
    )
    val uiState = _uiState.asStateFlow()

    private fun onPinPanelIconClicked() {

    }

    private fun onUserIconClicked() {

    }

    private fun onHomeScreenClicked() {

    }

}