package com.example.pokemonapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokemonapp.networkmodel.PokemonResult
import com.example.pokemonapp.viewmodel.PokemonState
import com.example.pokemonapp.viewmodel.PokemonViewModel

@Composable
fun PokemonPagedScreen(navController: NavController, pokemonViewModel: PokemonViewModel, userName: String) {
    val pokemonState by pokemonViewModel.pokemonState.collectAsState()
    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf("") } // ✅ Variable para la búsqueda

    // ✅ Cargar los primeros 20 Pokémon al iniciar la pantalla
    LaunchedEffect(Unit) {
        pokemonViewModel.fetchPokemonList()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "¡Bienvenido, $userName!", style = MaterialTheme.typography.titleLarge)

        Button(onClick = { navController.popBackStack() }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Regresar")
        }

        // ✅ Barra de búsqueda
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar Pokémon") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            when (pokemonState) {
                is PokemonState.Success -> {
                    val pokemonList = (pokemonState as PokemonState.Success).pokemonList
                    val filteredList = pokemonList.filter { it.name.contains(searchQuery, ignoreCase = true) } // ✅ Filtrar Pokémon

                    items(filteredList.size) { index ->
                        PokemonItem(filteredList[index]) {
                            navController.navigate("pokemonFeature/${filteredList[index].name}") // ✅ Navegar con el nombre
                        }
                    }

                    item {
                        Button(
                            onClick = { pokemonViewModel.fetchPokemonList() },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Text(text = "Cargar más Pokémon")
                        }
                    }
                }
                is PokemonState.Loading -> item { CircularProgressIndicator() }
                is PokemonState.Error -> item { Text(text = "Error: ${(pokemonState as PokemonState.Error).message}") }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonResult, function: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = function // ✅ Permitir interacción para navegar
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = pokemon.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}