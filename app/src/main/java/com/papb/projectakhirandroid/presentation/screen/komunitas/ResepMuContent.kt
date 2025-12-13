package com.papb.projectakhirandroid.presentation.screen.komunitas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.papb.projectakhirandroid.R
import com.papb.projectakhirandroid.ui.theme.* // =========================================================================
//                  KONTEN TAB: RESEP MU
// =========================================================================

@Composable
fun ResepMuContent(currentUserName: String, navController: NavController) {
    val itemsToDisplay = remember { listOf(
        // SIMULASI 1 DATA POSTINGAN RESEP milik pengguna saat ini
        mapOf(
            "id" to "RESEP_001",
            "caption" to "Resep Ayam Geprek Super Pedas!",
            "owner" to currentUserName // Postingan milik user
        )
    ) }
    val imageResourceId = R.drawable.food_resep

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = DIMENS_16dp, vertical = DIMENS_8dp),
        verticalArrangement = Arrangement.spacedBy(DIMENS_16dp)
    ) {
        // Judul dan Deskripsi Khusus Resep Mu
        item {
            Column(modifier = Modifier.padding(bottom = DIMENS_8dp)) {
                Text(
                    text = "Dapur Kreasi Resepmu",
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TEXT_SIZE_20sp,
                    color = Black
                )
                Text(
                    text = "Bagikan resep masakan andalanmu, tampilkan hasil karyamu, dan dapatkan inspirasi masakan harian dari komunitas FreshMart!",
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = TEXT_SIZE_14sp,
                    color = Color.Gray
                )
            }
        }

        items(itemsToDisplay) { post ->
            val postOwnerName = post["owner"] ?: currentUserName
            val isOwner = postOwnerName == currentUserName

            PostinganItem(
                username = postOwnerName,
                profileResId = R.drawable.profileimage,
                imageResId = imageResourceId,
                caption = post["caption"] ?: "",
                isOwner = isOwner,
                postId = post["id"] ?: "",
                navController = navController
            )
        }

        item {
            // Spacer untuk memberi ruang di atas FAB
            Spacer(modifier = Modifier.height(DIMENS_80dp))
        }
    }
}