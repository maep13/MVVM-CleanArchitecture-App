package com.example.pokemonapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RegistrationScreen(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var ageError by remember { mutableStateOf("") }

    val isFormValid = nameError.isEmpty() && emailError.isEmpty() && ageError.isEmpty() &&
            userName.isNotBlank() && email.isNotBlank() && age.isNotBlank()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // ✅ Centrar elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // ✅ Centrar elementos horizontalmente
    ) {
        TextField(
            value = userName,
            onValueChange = {
                userName = it
                nameError = if (userName.length < 3) "El nombre debe tener al menos 3 caracteres" else ""
            },
            label = { Text("Nombre") },
            isError = nameError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f) // ✅ Ajustar tamaño
        )
        if (nameError.isNotEmpty()) {
            Text(text = nameError, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Correo inválido" else ""
            },
            label = { Text("Correo Electrónico") },
            isError = emailError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        if (emailError.isNotEmpty()) {
            Text(text = emailError, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = age,
            onValueChange = {
                age = it
                ageError = if (age.toIntOrNull() == null || age.toInt() !in 18..99) "Edad debe estar entre 18 y 99 años" else ""
            },
            label = { Text("Edad") },
            isError = ageError.isNotEmpty(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        if (ageError.isNotEmpty()) {
            Text(text = ageError, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("pokemonPaged/$userName") },
            enabled = isFormValid, // ✅ Deshabilitar hasta que los datos sean válidos
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Continuar")
        }
    }
}