package com.example.miniproyecto01

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.miniproyecto01.data.PreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }

    // Para el DropdownMenu
    var expanded by remember { mutableStateOf(false) }
    val opcionesCarrera = listOf("Ing. de Software", "Sistemas", "Informática")
    var carrera by remember { mutableStateOf(opcionesCarrera[0]) }

    // Para RadioButton
    var turno by remember { mutableStateOf("Matutino") }

    // Para Switch
    var estatusActivo by remember { mutableStateOf(true) }

    // Cargar matrícula guardada al abrir
    LaunchedEffect(Unit) {
        matricula = preferencesManager.obtenerMatricula()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registro de Estudiantes", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        // DropdownMenu para Carrera
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = carrera,
                onValueChange = {},
                readOnly = true,
                label = { Text("Carrera") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcionesCarrera.forEach { seleccion ->
                    DropdownMenuItem(
                        text = { Text(seleccion) },
                        onClick = {
                            carrera = seleccion
                            expanded = false
                        }
                    )
                }
            }
        }

        // RadioButtons para Turno
        Text("Turno:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = turno == "Matutino", onClick = { turno = "Matutino" })
            Text("Matutino")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = turno == "Vespertino", onClick = { turno = "Vespertino" })
            Text("Vespertino")
        }

        // Switch para Estatus
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Estatus: ${if (estatusActivo) "Activo" else "Inactivo"}")
            Switch(checked = estatusActivo, onCheckedChange = { estatusActivo = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Guardar persistencia
                preferencesManager.guardarMatricula(matricula)

                // Preparar datos para enviar por navegación (Uri.encode evita que los espacios rompan la app)
                val nombreCodificado = Uri.encode(nombre)
                val carreraCodificada = Uri.encode(carrera)
                val estatusStr = if (estatusActivo) "Activo" else "Inactivo"

                // Navegar a Detalle
                navController.navigate("detail/$matricula/$nombreCodificado/$carreraCodificada/$turno/$estatusStr")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }
}