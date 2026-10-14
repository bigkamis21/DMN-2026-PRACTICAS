package com.example.miniproyecto02.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.miniproyecto02.data.Tarea
import com.example.miniproyecto02.domain.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).tareaDao() }
    val coroutineScope = rememberCoroutineScope()

    var nuevaTarea by remember { mutableStateOf("") }
    var menuExpandido by remember { mutableStateOf(false) }
    var ordenarPorEstado by remember { mutableStateOf(false) }

    // Leer datos reactivamente según el filtro seleccionado
    val tareas by if (ordenarPorEstado) {
        dao.obtenerOrdenadasPorEstado().collectAsState(initial = emptyList())
    } else {
        dao.obtenerTodas().collectAsState(initial = emptyList())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Tareas") },
                actions = {
                    IconButton(onClick = { menuExpandido = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menú Opciones")
                    }
                    DropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(if (ordenarPorEstado) "Ordenar por Fecha" else "Ordenar por Estado") },
                            onClick = {
                                ordenarPorEstado = !ordenarPorEstado
                                menuExpandido = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Borrar Completadas") },
                            onClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    dao.borrarCompletadas()
                                }
                                menuExpandido = false
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Fila para agregar tareas
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = nuevaTarea,
                    onValueChange = { nuevaTarea = it },
                    label = { Text("Nueva tarea") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (nuevaTarea.isNotBlank()) {
                        coroutineScope.launch(Dispatchers.IO) {
                            dao.insertar(Tarea(titulo = nuevaTarea))
                            withContext(Dispatchers.Main) { nuevaTarea = "" }
                        }
                    }
                }) {
                    Text("Agregar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de tareas
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tareas) { tarea ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Casilla para marcar completada (Actualizar)
                            Checkbox(
                                checked = tarea.completada,
                                onCheckedChange = { isChecked ->
                                    coroutineScope.launch(Dispatchers.IO) {
                                        dao.actualizar(tarea.copy(completada = isChecked))
                                    }
                                }
                            )
                            Text(
                                text = tarea.titulo,
                                modifier = Modifier.weight(1f)
                            )
                            // Botón para eliminar (Borrar)
                            IconButton(onClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    dao.eliminar(tarea)
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}