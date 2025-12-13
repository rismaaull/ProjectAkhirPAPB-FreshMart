package com.papb.projectakhirandroid.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.papb.projectakhirandroid.navigation.screen.BottomNavItemScreen
import com.papb.projectakhirandroid.navigation.screen.Screen
import com.papb.projectakhirandroid.presentation.screen.about.AboutScreen
import com.papb.projectakhirandroid.presentation.screen.cart.CartScreen
import com.papb.projectakhirandroid.presentation.screen.checkout.CheckoutScreen
import com.papb.projectakhirandroid.presentation.screen.collection.AddCollectionScreen
import com.papb.projectakhirandroid.presentation.screen.collection.CollectionScreen
import com.papb.projectakhirandroid.presentation.screen.collection.CollectionViewModel
import com.papb.projectakhirandroid.presentation.screen.detail.DetailScreen
import com.papb.projectakhirandroid.presentation.screen.editprofile.EditProfileScreen
import com.papb.projectakhirandroid.presentation.screen.explore.ExploreScreen
import com.papb.projectakhirandroid.presentation.screen.home.HomeViewModel
import com.papb.projectakhirandroid.presentation.screen.home.HomeScreen
import com.papb.projectakhirandroid.presentation.screen.home.clickToCart
import com.papb.projectakhirandroid.presentation.screen.invoice.InvoiceScreen
import com.papb.projectakhirandroid.presentation.screen.komunitas.KomunitasScreen
import com.papb.projectakhirandroid.presentation.screen.productlist.ProductListScreen
import com.papb.projectakhirandroid.presentation.screen.search.SearchScreen
import com.papb.projectakhirandroid.utils.Constants

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = BottomNavItemScreen.Home.route
    ) {
        composable(route = BottomNavItemScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomNavItemScreen.Explore.route) {
            ExploreScreen(navController = navController)
        }
        composable(route = BottomNavItemScreen.Cart.route) {
            CartScreen(navController = navController)
        }
        composable(route = BottomNavItemScreen.Komunitas.route) {
            KomunitasScreen()
        }
        composable(route = BottomNavItemScreen.About.route) {
            AboutScreen(navController = navController)
        }
        composable(route = Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        // Collection Graph
        collectionNavGraph(navController = navController)

        // Other screens
        composable(route = Screen.Checkout.route) {
            CheckoutScreen(navController = navController)
        }
        composable(route = Screen.Invoice.route) {
            InvoiceScreen(navController = navController)
        }
        composable(
            route = Screen.Search.route,
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
                defaultValue = ""
                nullable = true
            })
        ) {
            SearchScreen()
        }
        composable(
            route = Screen.ProductList.route,
            arguments = listOf(navArgument("title") {
                type = NavType.StringType
            })
        ) { entry ->
            val title = entry.arguments?.getString("title") ?: "Products"
            val homeViewModel: HomeViewModel = hiltViewModel()
            val context = LocalContext.current
            ProductListScreen(
                navController = navController,
                title = title,
                onClickToCart = { productItem ->
                    clickToCart(context, productItem, homeViewModel)
                }
            )
        }
        detailsNavGraph()
    }
}

fun NavGraphBuilder.collectionNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.Collection.route,
        route = "collection_graph"
    ) {
        composable(Screen.Collection.route) { backStackEntry ->
            val viewModel: CollectionViewModel = hiltViewModel(navController.getBackStackEntry("collection_graph"))
            CollectionScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.AddCollection.route) { backStackEntry ->
            val viewModel: CollectionViewModel = hiltViewModel(navController.getBackStackEntry("collection_graph"))
            AddCollectionScreen(navController = navController, viewModel = viewModel)
        }
    }
}

fun NavGraphBuilder.detailsNavGraph() {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.Details.route
    ) {
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(Constants.PRODUCT_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailScreen()
        }
    }
}
