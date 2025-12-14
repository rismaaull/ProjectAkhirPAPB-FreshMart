package com.papb.projectakhirandroid.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.papb.projectakhirandroid.navigation.graph.MainNavGraph
import com.papb.projectakhirandroid.presentation.component.BottomBar
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier // Wajib diimpor

// Tidak perlu @SuppressLint jika kita menggunakan padding parameter
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues -> // ✅ TANGKAP paddingValues DI SINI

        // ✅ PERBAIKAN: Terapkan paddingValues ke MainNavGraph
        MainNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues) // Menerapkan padding yang disediakan Scaffold
        )
    }
}