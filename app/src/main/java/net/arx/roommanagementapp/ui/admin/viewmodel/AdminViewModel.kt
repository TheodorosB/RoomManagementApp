package net.arx.roommanagementapp.ui.admin.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.admin.model.AdminUiState
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.cleaner.mapper.CleanerUiMapper
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiItem
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val cleanerUiMapper: CleanerUiMapper
): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        AdminUiState(
            onPinPanelIconClicked = { onPinPanelIconClicked() },
            onHomeScreenClicked = { onHomeScreenClicked() },
            onAddCleanerClicked = { onAddCleanerClicked() },
            onCleanerClicked = { onCleanerClicked() },
            onCloseAlertDialog = { onCloseAlertDialog() },
            onAddNewCleanerClicked = { onAddNewCleanerClicked() },
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value.cleaners.addAll(cleanerUiMapper())
    }

    private fun onPinPanelIconClicked() {

    }

    private fun onHomeScreenClicked() {

    }

    private fun onCleanerClicked() {

    }

    private fun onAddNewCleanerClicked() {
        _uiState.value.openAlertDialog.value = true
    }

    private fun onAddCleanerClicked() {
        val newCleanersName = _uiState.value.cleanerFormUiItem.name.value
        _uiState.value.cleaners.add(
            CleanerUiItem(name = newCleanersName)
        )
        _uiState.value.cleanerFormUiItem.resetForm()
        onCloseAlertDialog()
    }

    private fun onCloseAlertDialog() {
        _uiState.value.cleanerFormUiItem.resetForm()
        _uiState.value.openAlertDialog.value = false
    }

}