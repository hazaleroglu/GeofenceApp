package com.example.geofenceapp.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geofenceapp.data.GeofenceLocation
import com.example.geofenceapp.internal.util.GeofenceManager
import com.example.geofenceapp.internal.util.RequestLocationPermissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: GeofenceViewModel = viewModel() ) {
    RequestLocationPermissions()
    val infoText by viewModel.infoText.collectAsState()
    val geofenceLocations = listOf(
        GeofenceLocation("Home", 37.7749, -122.4194, 100f),
        GeofenceLocation("Office", 37.7849, -122.4094, 150f)
    )
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val geofenceManager = remember { GeofenceManager(context) }

    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            scope.launch(Dispatchers.IO) {
                geofenceManager.registerGeofence()
            }
        }
    }

    ShowMap(geofenceLocations = geofenceLocations, geofenceManager)
    Text(text = infoText)
}

@Composable
private fun ShowMap(geofenceLocations: List<GeofenceLocation>, geofenceManager: GeofenceManager) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.7749, -122.4194), 12f)
    }

    GoogleMap(
        properties = MapProperties(isMyLocationEnabled = true),
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLongClick = { latLng ->

        }
    ) {
        geofenceLocations.forEach { location ->
            // Geofence merkezini işaretleyin
            Marker(
                state = MarkerState(LatLng(location.latitude, location.longitude)),
                title = location.name
            )

            // Geofence alanını çizin
            Circle(
                center = LatLng(location.latitude, location.longitude),
                radius = location.radius.toDouble(),
                fillColor = Color(0x5500FF00),
                strokeColor = Color.Green,
                strokeWidth = 2f
            )
        }
    }
}
