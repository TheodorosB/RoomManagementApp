package net.arx.roommanagementapp.ui.dashboard.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.model.DashboardUiState
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        DashboardUiState(
            onNavigateToAdminClicked = { onNavigateToAdminClicked() },
            onNavigateToCleanerClicked = { onNavigateToCleanerClicked() }
        )
    )
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private fun onNavigateToCleanerClicked() {
        _uiState.value.backstackEntries.add(DashboardNavEntries.Cleaner)
    }

    private fun onNavigateToAdminClicked() {
        _uiState.value.backstackEntries.add(DashboardNavEntries.Admin)
    }

}