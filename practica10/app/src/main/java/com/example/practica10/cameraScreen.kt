package com.example.practica10

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamaraScreen() {
    val context = LocalContext.current
    var listaImagenes by remember { mutableStateOf(listOf<Bitmap>()) } // Lista observable de imágenes[cite: 10]

    // Lanzador para abrir la cámara y recibir el Bitmap capturado[cite: 10]
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            listaImagenes = listaImagenes + bitmap // Añade la foto a la lista[cite: 10]
        }
    }

    // RETO: Lanzador para solicitar permiso de cámara en tiempo de ejecución explícitamente
    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            camaraLauncher.launch(null) // Si se otorga, abre la cámara
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cámara y Galería") }, //[cite: 11]
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer //[cite: 11]
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // RETO: Verificación del permiso android.Manifest.permission.CAMERA antes de accionar[cite: 12]
                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    )
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        camaraLauncher.launch(null)
                    } else {
                        permisoLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tomar Foto") //[cite: 11]
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp) //[cite: 11]
        ) {
            if (listaImagenes.isEmpty()) {
                Text("No hay fotos en la galería. ¡Toma una!") //[cite: 11]
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), //[cite: 11]
                    verticalArrangement = Arrangement.spacedBy(8.dp), //[cite: 11]
                    horizontalArrangement = Arrangement.spacedBy(8.dp), //[cite: 11]
                    modifier = Modifier.fillMaxSize() //[cite: 11]
                ) {
                    items(listaImagenes) { bitmap ->
                        ImagenCapturadaItem(bitmap = bitmap) //[cite: 11]
                    }
                }
            }
        }
    }
}

@Composable
fun ImagenCapturadaItem(bitmap: Bitmap) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), //[cite: 11]
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), //[cite: 11]
        shape = RoundedCornerShape(8.dp) //[cite: 11]
    ) {
        // Convertimos el Bitmap a ImageBitmap para que Compose lo renderice[cite: 9, 11]
        Image(
            bitmap = bitmap.asImageBitmap(), //[cite: 11]
            contentDescription = "Foto capturada", //[cite: 11]
            contentScale = ContentScale.Crop, //[cite: 11]
            modifier = Modifier.fillMaxSize() //[cite: 11]
        )
    }
}