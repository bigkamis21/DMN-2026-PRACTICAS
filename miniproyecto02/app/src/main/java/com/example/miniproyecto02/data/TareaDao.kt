package com.example.miniproyecto02.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {
    // Ordenar por fecha por defecto
    @Query("SELECT * FROM tareas ORDER BY fechaCreacion DESC")
    fun obtenerTodas(): Flow<List<Tarea>>

    // Filtrar por estado
    @Query("SELECT * FROM tareas ORDER BY completada ASC, fechaCreacion DESC")
    fun obtenerOrdenadasPorEstado(): Flow<List<Tarea>>

    @Insert
    fun insertar(tarea: Tarea)

    @Update
    fun actualizar(tarea: Tarea) // Para editar o marcar como completada

    @Delete
    fun eliminar(tarea: Tarea)

    @Query("DELETE FROM tareas WHERE completada = 1")
    fun borrarCompletadas() // Acción para el ToolBar
}