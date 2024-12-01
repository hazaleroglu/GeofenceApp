package com.example.geofenceapp.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GeofenceViewModel : ViewModel() {
    private val _infoText = MutableStateFlow("No geofence activity yet.")
    val infoText: StateFlow<String> get() = _infoText

    fun updateInfoText(newText: String) {
        _infoText.value = newText
    }
}
