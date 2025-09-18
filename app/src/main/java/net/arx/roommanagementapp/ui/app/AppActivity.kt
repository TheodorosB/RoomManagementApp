package net.arx.roommanagementapp.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint
import net.arx.roommanagementapp.ui.dashboard.navigation.DashboardNavDisplay
import net.arx.roommanagementapp.ui.theme.RoomManagementAppTheme

@AndroidEntryPoint
class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            RoomManagementAppTheme {
                DashboardNavDisplay()
            }
        }
    }
}