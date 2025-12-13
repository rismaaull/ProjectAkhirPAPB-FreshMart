package com.papb.projectakhirandroid.presentation.screen.komunitas

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
// Hapus semua impor yang berhubungan dengan ViewModel dan Hilt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.papb.projectakhirandroid.ui.theme.* enum class KomunitasTab {
    RESEP_MU,
    TIPS_DAPUR
}

// =========================================================================
//                  KOMUNITAS SCREEN UTAMA
// =========================================================================

@Composable
fun KomunitasScreen(
    navController: NavController = rememberNavController()
    // Hapus parameter userViewModel: UserViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(KomunitasTab.RESEP_MU) }

    // NAMA PENGGUNA STATIS (Placeholder - Siap diganti jika ViewModel/State Profile ada)
    val currentUserName = "Nama Profil Anda"

    Scaffold(
        backgroundColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Navigasi ke AddPostScreen */ },
                backgroundColor = Green,
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = DIMENS_16dp, end = DIMENS_16dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Postingan")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // 1. HEADER (FIXED)
            HeaderKomunitas(
                title = "Forum FreshMart",
                description = "Tempat inspirasi resep harian, tips dapur, dan berbagi kreasi masakan Anda."
            )

            // 2. TAB ROW (FIXED)
            TabRowKomunitas(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            // 3. KONTEN TAB (SCROLLABLE)
            Crossfade(targetState = selectedTab, label = "KomunitasTabContent") { tab ->
                when (tab) {
                    // Konten diambil dari file Composable terpisah
                    KomunitasTab.RESEP_MU -> ResepMuContent(currentUserName = currentUserName, navController = navController)
                    KomunitasTab.TIPS_DAPUR -> TipsDapurContent(currentUserName = currentUserName, navController = navController)
                }
            }
        }
    }
}

// ---------------------- Komponen Header Utama Tetap ----------------------
@Composable
fun HeaderKomunitas(title: String, description: String) {
    Column(modifier = Modifier.padding(DIMENS_16dp)) {
        Text(
            text = title,
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = TEXT_SIZE_24sp,
            color = Black,
            modifier = Modifier.padding(bottom = DIMENS_4dp)
        )
        Text(
            text = description,
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = TEXT_SIZE_14sp,
            color = Color.Gray
        )
    }
    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = DIMENS_1dp)
}


// ---------------------- Komponen Tab Tetap ----------------------
@Composable
fun TabRowKomunitas(
    selectedTab: KomunitasTab,
    onTabSelected: (KomunitasTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIMENS_16dp)
            .clip(RoundedCornerShape(DIMENS_12dp))
            .background(GrayBackground),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabItem(
            text = "Resep Mu",
            isSelected = selectedTab == KomunitasTab.RESEP_MU,
            onClick = { onTabSelected(KomunitasTab.RESEP_MU) },
            modifier = Modifier.weight(1f)
        )
        TabItem(
            text = "Tips Dapur",
            isSelected = selectedTab == KomunitasTab.TIPS_DAPUR,
            onClick = { onTabSelected(KomunitasTab.TIPS_DAPUR) },
            modifier = Modifier.weight(1f)
        )
    }
}

// ---------------------- Komponen Item Tab ----------------------
@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(DIMENS_12dp))
            .background(if (isSelected) Green else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = DIMENS_8dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color.White else Black,
            fontSize = TEXT_SIZE_16sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun KomunitasScreenPreview() {
    KomunitasScreen()
}