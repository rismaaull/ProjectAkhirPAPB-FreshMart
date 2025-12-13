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
//                  KONTEN TAB: TIPS DAPUR
// =========================================================================

@Composable
fun TipsDapurContent(currentUserName: String, navController: NavController) {
    val itemsToDisplay = remember { listOf(
        // SIMULASI 1 DATA POSTINGAN TIPS DAPUR milik pengguna saat ini
        mapOf(
            "id" to "TIPS_001",
            "caption" to "Tips menyimpan daun seledri agar tetap segar!",
            "owner" to currentUserName // Postingan milik user
        )
    ) }
    val imageResourceId = R.drawable.food_tips

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = DIMENS_16dp, vertical = DIMENS_8dp),
        verticalArrangement = Arrangement.spacedBy(DIMENS_16dp)
    ) {
        // Judul dan Deskripsi Khusus Tips Dapur
        item {
            Column(modifier = Modifier.padding(bottom = DIMENS_8dp)) {
                Text(
                    text = "Gudang Rahasia Tips Dapur",
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TEXT_SIZE_20sp,
                    color = Black
                )
                Text(
                    text = "Temukan hacks dapur, trik mengolah bahan segar, dan cara menyimpan bahan makanan seperti daging, sayur, dan bumbu agar tahan lama!",
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