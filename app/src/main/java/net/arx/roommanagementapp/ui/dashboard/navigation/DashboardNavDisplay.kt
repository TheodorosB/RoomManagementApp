package net.arx.roommanagementapp.ui.dashboard.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import net.arx.roommanagementapp.ui.admin.composable.AdminScreen
import net.arx.roommanagementapp.ui.cleaner.composable.CleanerScreen
import net.arx.roommanagementapp.ui.dashboard.model.DashboardNavEntries
import net.arx.roommanagementapp.ui.dashboard.viewmodel.DashboardViewModel
import net.arx.roommanagementapp.ui.theme.ColorBaseBackground

@Composable
fun DashboardNavDisplay(
    modifier: Modifier = Modifier
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    NavDisplay(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = ColorBaseBackground)
            .padding(horizontal = 8.dp),
        backStack = uiState.value.backstackEntries,
        contentAlignment = Alignment.Center,
        entryProvider = { key ->
            when (key) {
                DashboardNavEntries.Admin -> NavEntry(
                    key = key,
                    content = {
                        AdminScreen(
                            navigateToCleaner = uiState.value.onNavigateToCleanerClicked
                        )
                    }
                )
                DashboardNavEntries.Cleaner -> NavEntry(
                    key = key,
                    content = {
                        CleanerScreen(
                            navigateToAdmin = uiState.value.onNavigateToAdminClicked
                        )
                    }
                )
            }
        }
    )
}