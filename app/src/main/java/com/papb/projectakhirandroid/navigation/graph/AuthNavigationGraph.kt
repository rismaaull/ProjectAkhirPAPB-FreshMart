
package com.papb.projectakhirandroid.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.papb.projectakhirandroid.navigation.screen.Screen
import com.papb.projectakhirandroid.presentation.screen.auth.LoginScreen
import com.papb.projectakhirandroid.presentation.screen.auth.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
    }
}
