package com.papb.projectakhirandroid.presentation.screen.komunitas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.papb.projectakhirandroid.R
import com.papb.projectakhirandroid.ui.theme.* // =========================================================================
//                  HELPER FUNCTIONS (NAVIGASI & AKSI)
// =========================================================================

fun navigateToDetail(navController: NavController, postId: String) {
    // TODO: GANTI INI DENGAN LOGIKA NAVIGASI COMPOSE REAL
    println(">>> NAVIGASI: Ke Detail Post $postId")
}

fun deletePost(postId: String) {
    // TODO: GANTI INI DENGAN LOGIKA PENGHAPUSAN DI VIEWMODEL/DATABASE
    println(">>> AKSI: Menghapus Postingan $postId")
}

// =========================================================================
//                  KOMPONEN ITEM POSTINGAN
// =========================================================================

@Composable
fun PostinganItem(
    username: String,
    profileResId: Int,
    imageResId: Int,
    caption: String,
    isOwner: Boolean,
    postId: String,
    navController: NavController
) {
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navigateToDetail(navController, postId) }) // Klik untuk Detail
            .clip(RoundedCornerShape(DIMENS_12dp))
            .background(Color.White)
            .border(DIMENS_1dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(DIMENS_12dp))
    ) {
        // Area Gambar Postingan
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Postingan gambar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(DIMENS_248dp)
        )

        // Area Profil, Caption, dan Opsi Hapus
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DIMENS_12dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Gambar Profil + Nama Pengguna + Caption
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Gambar Profil
                Image(
                    painter = painterResource(id = profileResId),
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(DIMENS_40dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(DIMENS_8dp))

                // Nama Pengguna dan Caption
                Column {
                    Text(
                        text = username,
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TEXT_SIZE_16sp,
                        color = Black
                    )
                    Spacer(modifier = Modifier.height(DIMENS_2dp))
                    Text(
                        text = caption,
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = TEXT_SIZE_14sp,
                        color = Color.DarkGray
                    )
                }
            }

            // OPSI HAPUS (Hanya muncul jika isOwner = true)
            if (isOwner) {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Opsi",
                        tint = Color.Gray
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        deletePost(postId) // Panggil fungsi delete
                        showMenu = false
                    }) {
                        Text("Hapus Postingan", color = Color.Red)
                    }
                }
            }
        }
    }
}