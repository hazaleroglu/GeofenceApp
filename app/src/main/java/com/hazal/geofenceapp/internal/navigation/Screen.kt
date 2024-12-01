package com.hazal.geofenceapp.internal.navigation

import androidx.navigation.NamedNavArgument

enum class AllScreens {
    HOME
}

sealed class Screen(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()
) {
    object Home: Screen(
        route = AllScreens.HOME.name
    ) {
        fun createRoute() = AllScreens.HOME.name
    }
}