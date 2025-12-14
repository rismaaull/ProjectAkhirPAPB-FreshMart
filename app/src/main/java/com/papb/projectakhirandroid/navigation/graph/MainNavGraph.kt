// File: app/src/main/java/com/papb/projectakhirandroid/navigation/graph/MainNavGraph.kt

package com.papb.projectakhirandroid.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.papb.projectakhirandroid.presentation.screen.detail.DetailScreen
import com.papb.projectakhirandroid.presentation.screen.editprofile.EditProfileScreen
import com.papb.projectakhirandroid.presentation.screen.explore.ExploreScreen
import com.papb.projectakhirandroid.presentation.screen.home.HomeViewModel
import com.papb.projectakhirandroid.presentation.screen.home.HomeScreen
import com.papb.projectakhirandroid.presentation.screen.home.clickToCart
import com.papb.projectakhirandroid.presentation.screen.invoice.InvoiceScreen
import com.papb.projectakhirandroid.presentation.screen.komunitas.AddPostScreen
import com.papb.projectakhirandroid.presentation.screen.komunitas.KomunitasScreen
import com.papb.projectakhirandroid.presentation.screen.productlist.ProductListScreen
import com.papb.projectakhirandroid.presentation.screen.search.SearchScreen
import com.papb.projectakhirandroid.utils.Constants.PRODUCT_ARGUMENT_KEY

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = BottomNavItemScreen.Home.route,
        modifier = modifier // Menerapkan padding (termasuk padding bottom) ke NavHost
    ) {
        // --- Bottom Navigation Items ---
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
            KomunitasScreen(navController = navController)
        }
        composable(route = BottomNavItemScreen.About.route) {
            AboutScreen(navController = navController)
        }

        // --- Other Screens ---
        composable(route = Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        // ADD POST SCREEN
        composable(
            route = Screen.AddPost.route,
            arguments = listOf(navArgument("postType") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val postType = backStackEntry.arguments?.getString("postType") ?: "resep"
            AddPostScreen(
                navController = navController,
                postType = postType
            )
        }

        // Checkout and Invoice Screens
        composable(route = Screen.Checkout.route) {
            CheckoutScreen(navController = navController)
        }
        composable(route = Screen.Invoice.route) {
            InvoiceScreen(navController = navController)
        }

        // Search Graph
        composable(
            route = Screen.Search.route,
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
                defaultValue = ""
                nullable = true
            })
        ) {
            // ✅ PERBAIKAN 1 (Baris 97): Hapus parameter navController
            SearchScreen()
        }

        // Product List Graph (Category / See All)
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

        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.Details.route
    ) {
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(PRODUCT_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            // ✅ PERBAIKAN 2 (Baris 138): Hapus parameter navController
            DetailScreen()
        }
    }
}