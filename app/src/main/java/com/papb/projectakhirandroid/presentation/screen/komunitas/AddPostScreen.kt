package com.papb.projectakhirandroid.presentation.screen.komunitas

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.papb.projectakhirandroid.domain.model.Post
import com.papb.projectakhirandroid.ui.theme.*

@Composable
fun AddPostScreen(
    navController: NavController,
    postType: String,
    viewModel: KomunitasViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // ✅ PERBAIKAN: Placeholder untuk 'owner'
    val currentUserName = "Nama Pengguna Anda"

    val topBarTitle = "Buat Postingan ${if (postType == "resep") "Resep Baru" else if (postType == "tips") "Tips Baru" else "Komunitas"}"

    // Fungsi untuk menangani submission post
    val handlePostSubmission: () -> Unit = {
        if (title.isNotBlank() && description.isNotBlank() && currentUserName.isNotBlank()) {
            val newPost = Post(
                type = postType,
                title = title,
                description = description,
                imageUrl = selectedImageUri,
                owner = currentUserName // ✅ Sekarang dikenali
            )
            viewModel.addPost(newPost)
            navController.popBackStack()
        } else {
            println("Tolong lengkapi Judul, Deskripsi, dan pastikan Anda login.")
        }
    }

    // Launcher untuk memilih gambar dari galeri
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                val contentResolver = context.contentResolver
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION

                try {
                    contentResolver.takePersistableUriPermission(uri, takeFlags)
                } catch (e: SecurityException) {
                    println("Security Exception: Failed to take persistable URI permission. Error: ${e.message}")
                }
                selectedImageUri = uri
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        topBarTitle,
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Black
                        )
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
                    label = { Text("Judul Postingan") },
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
                    textStyle = TextStyle(fontSize = TEXT_SIZE_14sp),
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
                // 3. UPLOAD GAMBAR (Diimpor dari KomunitasComponents.kt)
                UploadImageArea(
                    selectedImageUri = selectedImageUri,
                    onImagePicked = { imagePickerLauncher.launch("image/*") },
                    onRemoveImage = { selectedImageUri = null }
                )
            }

            item {
                Spacer(modifier = Modifier.height(DIMENS_8dp))
            }

            item {
                // 4. TOMBOL KIRIM
                Button(
                    onClick = handlePostSubmission,
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