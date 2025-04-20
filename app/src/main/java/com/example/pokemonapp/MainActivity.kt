package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.pokemonapp.view.navigation.PokemonAppNavigation
import com.example.pokemonapp.viewmodel.PokemonViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pokemonViewModel: PokemonViewModel = hiltViewModel() // ✅ Se inyecta en `MainActivity`
            PokemonAppNavigation(pokemonViewModel) // ✅ Se pasa a la navegación
        }
    }
}

