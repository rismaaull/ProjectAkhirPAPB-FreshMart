package com.papb.projectakhirandroid.presentation.screen.komunitas

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papb.projectakhirandroid.R // Import R untuk mengakses resource ID
import com.papb.projectakhirandroid.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class KomunitasViewModel @Inject constructor(
    // private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        loadInitialPosts()
    }

    private fun loadInitialPosts() {
        viewModelScope.launch {
            val packageName = R::class.java.getPackage()?.name ?: ""

            val dummyPosts = listOf(
                Post(
                    id = 1,
                    type = "resep",
                    title = "Nasi Goreng Sehat",
                    description = "Resep nasi goreng rendah garam, lezat tanpa MSG tambahan. Cocok untuk diet harian!",
                    // Menggunakan food_resep
                    imageUrl = Uri.parse("android.resource://$packageName/${R.drawable.food_resep}"),
                    owner = "admin_chef"
                ),
                Post(
                    id = 2,
                    type = "tips",
                    title = "Cara Menyimpan Sayur",
                    description = "Tips agar sayuran tetap segar lebih lama. Simpan di wadah kedap udara yang dilapisi tisu dapur.",
                    // Menggunakan food_tips
                    imageUrl = Uri.parse("android.resource://$packageName/${R.drawable.food_tips}"),
                    owner = "admin_dapur"
                )
            )
            _posts.value = dummyPosts
            // --- GANTI DENGAN LOGIKA REPOSITORY ANDA ---
        }
    }

    fun addPost(post: Post) {
        // Logika menambahkan post ke repository
    }
}