package com.papb.projectakhirandroid.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.papb.projectakhirandroid.navigation.screen.Screen
import com.papb.projectakhirandroid.presentation.screen.collection.AddCollectionScreen
import com.papb.projectakhirandroid.presentation.screen.collection.CollectionScreen

fun NavGraphBuilder.collectionGraph(navController: NavController) {
    navigation(
        route = "collection_graph",
        startDestination = Screen.Collection.route
    ) {
        composable(route = Screen.Collection.route) {
            CollectionScreen(navController = navController)
        }
        composable(route = Screen.AddCollection.route) {
            AddCollectionScreen(navController = navController)
        }
    }
}