package com.example.practica004

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Importa tus componentes personalizados
import com.example.practica004.components.*

@Composable
fun FormScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Práctica 4: Componentes Avanzados",
            fontSize = 22.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        HorizontalDivider()

        Text("1. Switch", style = MaterialTheme.typography.titleMedium)
        CustomSwitch()

        HorizontalDivider()

        Text("2. RadioButton", style = MaterialTheme.typography.titleMedium)
        CustomRadioButton()

        HorizontalDivider()

        Text("3. Checkbox", style = MaterialTheme.typography.titleMedium)
        CustomCheckbox()

        HorizontalDivider()

        Text("4. Spinner (DropdownMenu)", style = MaterialTheme.typography.titleMedium)
        CustomSpinner()

        HorizontalDivider()

        Text("5. DatePicker", style = MaterialTheme.typography.titleMedium)
        CustomDatePicker()

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Formulario completo", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Probar Formulario")
        }
    }
}