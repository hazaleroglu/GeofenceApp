package com.hazal.geofenceapp.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.hazal.geofenceapp.internal.util.RequestLocationPermissions
import com.hazal.geofenceapp.ui.screens.splash.SplashScreenViewModel

class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        setContent {
            GeofenceContent(
                splashViewModel = splashViewModel,
                onAppReady = {
                    GeofenceApp(startDestination = it)
                }
            )
        }
    }
}

@Composable
fun GeofenceContent(
    splashViewModel: SplashScreenViewModel,
    onAppReady: @Composable (String) -> Unit,
) {
    val startDestination by splashViewModel.startDestination.collectAsState()
    val continueState by splashViewModel.onContinue.collectAsState()

    splashViewModel.ShowNotificationPermission()
    RequestLocationPermissions()
    if (continueState) {
        onAppReady(startDestination)
    }

}