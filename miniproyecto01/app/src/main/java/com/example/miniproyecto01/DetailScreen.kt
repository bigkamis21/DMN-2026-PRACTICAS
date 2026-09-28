package com.example.miniproyecto01

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(
    matricula: String,
    nombre: String,
    carrera: String,
    turno: String,
    estatus: String
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Datos del Estudiante", fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider()

        Text("Matrícula: $matricula", fontSize = 18.sp)
        Text("Nombre: $nombre", fontSize = 18.sp)
        Text("Carrera: $carrera", fontSize = 18.sp)
        Text("Turno: $turno", fontSize = 18.sp)
        Text("Estatus: $estatus", fontSize = 18.sp)
    }
}