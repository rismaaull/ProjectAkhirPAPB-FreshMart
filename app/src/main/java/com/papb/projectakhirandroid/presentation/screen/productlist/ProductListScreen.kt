package com.papb.projectakhirandroid.presentation.screen.productlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.papb.projectakhirandroid.domain.model.ProductItem
import com.papb.projectakhirandroid.presentation.common.content.ListContentProduct
import com.papb.projectakhirandroid.ui.theme.GilroyFontFamily
import com.papb.projectakhirandroid.ui.theme.TEXT_SIZE_18sp
import com.papb.projectakhirandroid.utils.DataDummy

@Composable
fun ProductListScreen(
    navController: NavController,
    title: String,
    onClickToCart: (ProductItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TEXT_SIZE_18sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Di sini kita bisa memfilter produk berdasarkan kategori jika perlu.
            // Untuk saat ini, saya akan menampilkan semua produk dummy atau memfilternya secara sederhana.
            // Idealnya data ini diambil dari ViewModel berdasarkan kategori.

            val allProducts = DataDummy.generateDummyProduct()
            val filteredProducts = if (title == "See All" || title == "Semua Produk") {
                allProducts
            } else {
                // Filter sederhana: Jika judul kategori ada di deskripsi atau nama produk (Logic Dummy)
                // Atau tampilkan semua untuk simulasi jika tidak ada data kategori spesifik di ProductItem
                allProducts // Sementara tampilkan semua, nanti bisa diimprovisasi filter by category
            }

            ListContentProduct(
                title = "", // Judul sudah di TopBar
                products = filteredProducts,
                navController = navController,
                onClickToCart = onClickToCart
            )
        }
    }
}