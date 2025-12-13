package com.papb.projectakhirandroid.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.papb.projectakhirandroid.presentation.screen.MainScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION // DIUBAH: Langsung ke otentikasi
    ) {
        // Rute Splash dan Onboarding bisa dihapus jika tidak lagi digunakan
        // composable(route = Screen.Splash.route) {
        //     SplashScreen(navController = navController)
        // }
        // composable(route = Screen.OnBoarding.route) {
        //     OnBoardingScreen(navController = navController)
        // }

        authNavGraph(navController = navController)

        composable(route = Graph.MAIN) {
            MainScreen()
        }
    }
}
