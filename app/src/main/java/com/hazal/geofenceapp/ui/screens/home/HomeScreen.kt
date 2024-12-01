package com.hazal.geofenceapp.ui.screens.home

import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hazal.geofenceapp.data.GeofenceLocation
import com.hazal.geofenceapp.internal.util.GeofenceManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(viewModel: GeofenceViewModel = viewModel() ) {
    val infoText by viewModel.infoText.collectAsState()

    val context = LocalContext.current
    val geofenceManager = remember { GeofenceManager(context) }

    ShowMap(geofenceManager, context)
    Text(text = infoText)
}

@Composable
private fun ShowMap(geofenceManager: GeofenceManager, context: Context) {
    val geofenceLocations = remember { mutableStateListOf<GeofenceLocation>() }
    geofenceLocations.add(GeofenceLocation("Home", 37.7749, -122.4194, 100f))
    geofenceLocations.add(GeofenceLocation("Office", 37.7849, -122.4094, 150f))

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.7749, -122.4194), 12f)
    }

    GoogleMap(
        properties = MapProperties(isMyLocationEnabled = true),
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLongClick = { latLng ->
            val key = "Geofence_${latLng.latitude}_${latLng.longitude}"
            val location = Location("").apply {
                latitude = latLng.latitude
                longitude = latLng.longitude
            }

            geofenceManager.addGeofence(
                key = key,
                location = location
            )

            geofenceManager.registerGeofence()

            Toast.makeText(context, "Geofence Added at ${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()

            geofenceLocations.add(GeofenceLocation(key, location.latitude, location.longitude, 100f))
        }
    ) {
        geofenceLocations.forEach { location ->
            Marker(
                state = MarkerState(LatLng(location.latitude, location.longitude)),
                title = location.name
            )

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
