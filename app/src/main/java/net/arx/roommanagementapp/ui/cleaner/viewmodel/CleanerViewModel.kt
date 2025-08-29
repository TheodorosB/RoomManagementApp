package net.arx.roommanagementapp.ui.cleaner.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.arx.roommanagementapp.ui.base.BaseViewModel
import net.arx.roommanagementapp.ui.cleaner.mapper.CleanerUiMapper
import net.arx.roommanagementapp.ui.cleaner.model.CleanerUiState
import javax.inject.Inject

@HiltViewModel
class CleanerViewModel @Inject constructor(
    private val cleanerUiMapper: CleanerUiMapper
): BaseViewModel() {

    private val _uiState = MutableStateFlow(CleanerUiState())
    val uiState = _uiState.asStateFlow()

}