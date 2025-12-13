package com.papb.projectakhirandroid.presentation.screen.komunitas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.papb.projectakhirandroid.R
import com.papb.projectakhirandroid.ui.theme.* @Composable
fun AddPostScreen(navController: NavController = rememberNavController()) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Buat Postingan Baru",
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                backgroundColor = Color.White,
                elevation = DIMENS_4dp
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(DIMENS_16dp),
            verticalArrangement = Arrangement.spacedBy(DIMENS_16dp)
        ) {
            item {
                // 1. INPUT JUDUL
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Postingan (Resep/Tips)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(DIMENS_8dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Green,
                        unfocusedBorderColor = Color.Gray
                    ),
                    singleLine = true
                )
            }

            item {
                // 2. INPUT DESKRIPSI/ISI
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Deskripsi atau Langkah-langkah Lengkap") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DIMENS_160dp),
                    shape = RoundedCornerShape(DIMENS_8dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Green,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }

            item {
                // 3. UPLOAD GAMBAR
                UploadImageArea(
                    selectedImageUri = selectedImageUri,
                    onImagePicked = { uri -> selectedImageUri = uri },
                    onRemoveImage = { selectedImageUri = null }
                )
            }

            item {
                Spacer(modifier = Modifier.height(DIMENS_8dp))
            }

            item {
                // 4. TOMBOL KIRIM
                Button(
                    onClick = {
                        if (title.isNotBlank() && description.isNotBlank()) {
                            println("--- POSTINGAN BARU DIKIRIM ---")
                            navController.popBackStack()
                        } else {
                            println("Tolong lengkapi Judul dan Deskripsi.")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DIMENS_48dp),
                    shape = RoundedCornerShape(DIMENS_8dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green)
                ) {
                    Text("Kirim Postingan", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// =========================================================================
//                  KOMPONEN UPLOAD GAMBAR
// =========================================================================

@Composable
fun UploadImageArea(
    selectedImageUri: String?,
    onImagePicked: (String) -> Unit,
    onRemoveImage: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (selectedImageUri == null) DIMENS_160dp else DIMENS_200dp),
        shape = RoundedCornerShape(DIMENS_12dp),
        elevation = DIMENS_2dp
    ) {
        if (selectedImageUri == null) {
            // Tampilan saat belum ada gambar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        DIMENS_2dp,
                        Color.LightGray.copy(alpha = 0.7f),
                        RoundedCornerShape(DIMENS_12dp)
                    )
                    .clickable {
                        // SIMULASI: Anggap gambar terpilih
                        onImagePicked("content://simulasi/image_12345")
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.CloudUpload,
                    contentDescription = "Upload Gambar",
                    modifier = Modifier.size(DIMENS_48dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.height(DIMENS_8dp))
                Text(
                    "Ketuk untuk Upload Gambar Masakan Anda",
                    color = Color.Gray,
                    fontFamily = GilroyFontFamily
                )
            }
        } else {
            // Tampilan saat gambar sudah terpilih
            Box(modifier = Modifier.fillMaxSize()) {
                // SIMULASI: Gunakan placeholder image (pastikan Anda punya R.drawable.food_placeholder)
                Image(
                    painter = painterResource(id = R.drawable.food_resep),
                    contentDescription = "Gambar Terpilih",
                    modifier = Modifier.fillMaxSize()
                )

                // Tombol Hapus Gambar
                IconButton(
                    onClick = onRemoveImage,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(DIMENS_8dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Hapus Gambar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPostScreenPreview() {
    AddPostScreen()
}