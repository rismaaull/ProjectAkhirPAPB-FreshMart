package com.papb.projectakhirandroid.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Anda harus memastikan file-file berikut ada dan diimpor dengan benar.
import com.papb.projectakhirandroid.navigation.screen.Screen // Asumsi: Lokasi file Screen.kt
import com.papb.projectakhirandroid.presentation.screen.MainScreen
import com.papb.projectakhirandroid.presentation.screen.onboarding.OnBoardingScreen
import com.papb.projectakhirandroid.presentation.screen.splash.SplashScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT, // Menggunakan Graph dari Graph.kt
        startDestination = Screen.Splash.route // Menggunakan Screen dari Screen.kt
    ) {
        // Rute: SPLASH SCREEN
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // Rute: ONBOARDING SCREEN
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(navController = navController)
        }

        // Rute: MAIN GRAPH (NAVIGASI BERSARANG)
        composable(route = Graph.MAIN) {
            MainScreen()
        }
    }
}