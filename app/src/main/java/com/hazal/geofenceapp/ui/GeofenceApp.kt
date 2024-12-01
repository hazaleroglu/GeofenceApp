package com.hazal.geofenceapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hazal.geofenceapp.internal.navigation.Screen
import com.hazal.geofenceapp.ui.screens.home.HomeScreen
import com.hazal.geofenceapp.ui.theme.GeofenceAppTheme

@Composable
fun GeofenceApp(startDestination: String) {
    GeofenceAppTheme {

        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = startDestination
            ) {
                geofenceAppNavGraph(navController)
            }
        }
    }
}
private fun NavGraphBuilder.geofenceAppNavGraph(
    navController: NavHostController
) {
    composable(Screen.Home.route) {
        HomeScreen()
    }
}