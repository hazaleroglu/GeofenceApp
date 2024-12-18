package com.hazal.geofenceapp.internal.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("osman", "broadcast")
        if (intent != null) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent != null && context != null){
                if (geofencingEvent.hasError()) {
                    Log.e("Geofence", "Geofence error: ${geofencingEvent.errorCode}")
                    return
                }

                val transitionType = geofencingEvent.geofenceTransition
                val locationName = geofencingEvent.triggeringGeofences?.firstOrNull()?.requestId

                if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                    Log.d("Geofence", "Entered: $locationName")
                    showNotification(context, "Geofence Entered", "Exited $locationName")
                } else if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
                    Log.d("Geofence", "Exited: $locationName")
                    showNotification(context, "Geofence Exited", "Exited $locationName")
                }
            }
        }
    }

    companion object {
        const val ACTION_GEOFENCE_EVENT = "GEOFENCE_EVENT"
    }
}
