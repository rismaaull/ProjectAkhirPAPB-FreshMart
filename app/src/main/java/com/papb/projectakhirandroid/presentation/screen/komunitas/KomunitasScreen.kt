package com.papb.projectakhirandroid.presentation.screen.komunitas

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.papb.projectakhirandroid.ui.theme.*
import kotlinx.coroutines.launch

// Daftar tab
private val tabItems = listOf("RESEP MU", "TIPS DIET")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun KomunitasScreen(
    navController: NavController,
    viewModel: KomunitasViewModel = hiltViewModel()
) {
    // ⚠️ GANTI DENGAN DATA USER ASLI DARI VIEWMODEL / SESSION
    val currentUserName = "user_yang_sedang_login"

    val allPosts by viewModel.posts.collectAsState()

    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Komunitas FreshMart",
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                },
                backgroundColor = Color.White,
                elevation = DIMENS_4dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add_post_screen/resep")
                },
                backgroundColor = Green,
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Tambah Postingan",
                    tint = Color.White
                )
            }
        },
        isFloatingActionButtonDocked = false
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            // ✅ PERBAIKAN: Hanya ambil padding atas (untuk TopBar)
            .padding(top = paddingValues.calculateTopPadding())
        ) {

            // Tab Bar (Freeze / Sticky Header)
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.White,
                // ✅ Warna indikator aktif adalah Hijau
                contentColor = Green
            ) {
                tabItems.forEachIndexed { index, title ->
                    val isSelected = pagerState.currentPage == index
                    Tab(
                        text = {
                            Text(
                                title,
                                fontFamily = GilroyFontFamily,
                                // ✅ Warna teks tab selalu Hitam
                                color = Black,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }

            // Konten Tab (HorizontalPager)
            HorizontalPager(
                count = tabItems.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val filteredPosts = remember(allPosts, page) {
                    when (page) {
                        0 -> allPosts.filter { it.type == "resep" }
                        1 -> allPosts.filter { it.type == "tips" }
                        else -> emptyList()
                    }
                }

                when (page) {
                    0 -> ResepMuContent(
                        currentUserName = currentUserName,
                        navController = navController,
                        posts = filteredPosts
                    )
                    1 -> TipsDapurContent(
                        currentUserName = currentUserName,
                        navController = navController,
                        posts = filteredPosts
                    )
                }
            }
        }
    }
}