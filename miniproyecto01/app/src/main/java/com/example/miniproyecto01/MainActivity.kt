package com.example.miniproyecto01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "form") {
                        // Pantalla 1
                        composable("form") {
                            FormScreen(navController)
                        }

                        // Pantalla 2 (Recibiendo argumentos)
                        composable(
                            route = "detail/{matricula}/{nombre}/{carrera}/{turno}/{estatus}",
                            arguments = listOf(
                                navArgument("matricula") { type = NavType.StringType },
                                navArgument("nombre") { type = NavType.StringType },
                                navArgument("carrera") { type = NavType.StringType },
                                navArgument("turno") { type = NavType.StringType },
                                navArgument("estatus") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            DetailScreen(
                                matricula = backStackEntry.arguments?.getString("matricula") ?: "",
                                nombre = backStackEntry.arguments?.getString("nombre") ?: "",
                                carrera = backStackEntry.arguments?.getString("carrera") ?: "",
                                turno = backStackEntry.arguments?.getString("turno") ?: "",
                                estatus = backStackEntry.arguments?.getString("estatus") ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}

