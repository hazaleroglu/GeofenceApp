package com.example.geofenceapp.ui.screens.splash

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModel
import com.example.geofenceapp.internal.navigation.AllScreens
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
                _onContinue.value = true
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
    @Composable
    fun ShowPermissionsss() {
        val context = LocalContext.current

        val neededPermissions = arrayOf(
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS,
        )

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { maps ->
                val isGranted = maps.values.reduce { acc, next -> (acc && next) }
                if (isGranted) {
                    _onContinue.value = true
                }
                maps.forEach { entry ->
                    Log.i("Permission = ${entry.key}", "Enabled ${entry.value}")
                }
            }

        LaunchedEffect(key1 = Unit) {
            when {
                hasPermissions(context, *neededPermissions) -> {
                    // All permissions granted
                    _onContinue.value = true

                }
                else -> {
                    // Request permissions
                    launcher.launch(neededPermissions)
                }
            }
        }
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
}