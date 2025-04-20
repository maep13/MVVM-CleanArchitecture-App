package com.example.pokemonapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokemonapp.viewmodel.PokemonViewModel
import com.example.pokemonapp.networkmodel.ApiResult

@Composable
fun PokemonFeatureScreen(
    navController: NavController,
    pokemonName: String,
) {
    val pokemonViewModel: PokemonViewModel = hiltViewModel() // ✅ Obtener el ViewModel correctamente
    val pokemonDetailState = pokemonViewModel.fetchPokemonDetail(pokemonName).collectAsState(initial = ApiResult.Loading) // ✅ Manejo correcto de `Loading`

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Regresar")
        }

        when (val result = pokemonDetailState.value) { // ✅ Manejar `ApiResult` adecuadamente
            is ApiResult.Success -> {
                val pokemon = result.data
                Spacer(modifier = Modifier.height(16.dp))

                val imageUrl = pokemon.imageUrl.ifEmpty { "https://example.com/default_image.png" } // ✅ Imagen por defecto

                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = "Imagen de ${pokemon.name}",
                    modifier = Modifier.size(120.dp)
                )

                Text(text = "#${pokemon.id} - ${pokemon.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Tipos: ${pokemon.types.joinToString(", ")}")
                Text(text = "Habilidades: ${pokemon.abilities.joinToString(", ")}")
                Text(text = "Estadísticas:")

                if (pokemon.stats.isNotEmpty()) {
                    pokemon.stats.forEach { stat ->
                        Text(text = "${stat.name}: ${stat.baseStat}")
                        println("Debug - Stat: ${stat.name}, Valor: ${stat.baseStat}") // ✅ Log para verificar valores reales
                    }
                } else {
                    Text(text = "No hay estadísticas disponibles.")
                }
            }
            is ApiResult.Error -> Text(text = "Error: ${result.message}", style = MaterialTheme.typography.bodyMedium) // ✅ Manejo de error
            is ApiResult.Loading -> Text("Cargando datos...", style = MaterialTheme.typography.bodyMedium) // ✅ Estado de carga
        }
    }
}