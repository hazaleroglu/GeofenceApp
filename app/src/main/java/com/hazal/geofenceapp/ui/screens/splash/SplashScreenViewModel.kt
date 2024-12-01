package com.hazal.geofenceapp.ui.screens.splash

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModel
import com.hazal.geofenceapp.internal.navigation.AllScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashScreenViewModel() : ViewModel() {

    private val _onContinue = MutableStateFlow(false)
    val onContinue: StateFlow<Boolean> = _onContinue

    private val _startDestination = MutableStateFlow(AllScreens.HOME.name)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    @Composable
    fun ShowNotificationPermission() {
        val context = LocalContext.current

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) { _onContinue.value = true }
            }

        LaunchedEffect(key1 = Unit) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                // Permission is already granted, proceed
                _onContinue.value = true
            } else {
                // Launch the native permission dialog
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}