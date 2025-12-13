package com.papb.projectakhirandroid.presentation.screen.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import com.papb.projectakhirandroid.R
import com.papb.projectakhirandroid.navigation.screen.BottomNavItemScreen
import com.papb.projectakhirandroid.navigation.screen.Screen
import com.papb.projectakhirandroid.ui.theme.GilroyFontFamily
import com.papb.projectakhirandroid.ui.theme.Green

@Composable
fun CollectionScreen(
    navController: NavController,
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val collections by viewModel.collections.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Koleksi Saya") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(BottomNavItemScreen.About.route) {
                            // Pop up to the start destination of the graph to avoid building up a large back stack
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                backgroundColor = Green,
                contentColor = Color.Black
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddCollection.route) },
                backgroundColor = Green
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Tambah Koleksi", tint = Color.Black)
            }
        }
    ) { padding ->
        if (collections.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Koleksi Anda masih kosong.\nSilakan tambahkan koleksi baru.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(collections) { collection ->
                    CollectionCard(collection = collection)
                }
            }
        }
    }
}

@Composable
fun CollectionCard(collection: CollectionItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) { 
        Column {
            Image(
                painter = rememberAsyncImagePainter(
                    model = collection.imageUri,
                    error = painterResource(id = R.drawable.profile_picture_placeholder) // Fallback image
                ),
                contentDescription = collection.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )
            Text(
                text = collection.name,
                modifier = Modifier.padding(8.dp),
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black // Ensure text is black
            )
        }
    }
}
