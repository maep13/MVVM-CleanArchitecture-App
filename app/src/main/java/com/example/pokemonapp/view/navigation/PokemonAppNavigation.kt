package com.example.pokemonapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonapp.view.PokemonFeatureScreen
import com.example.pokemonapp.view.PokemonPagedScreen
import com.example.pokemonapp.view.RegistrationScreen
import com.example.pokemonapp.viewmodel.PokemonViewModel

@Composable
fun PokemonAppNavigation(pokemonViewModel: PokemonViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "registration") {
        composable("registration") { RegistrationScreen(navController) }

        composable(
            "pokemonPaged/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType }) // ✅ Define `userName` como argumento
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: "Entrenador Desconocido"
            PokemonPagedScreen(navController, pokemonViewModel, userName) // ✅ Se pasan `pokemonViewModel` y `userName`
        }

        composable(
            "pokemonFeature/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType }) // ✅ Define `pokemonName` como argumento
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: "Desconocido"
            PokemonFeatureScreen(navController, pokemonName) // ✅ Se pasa `pokemonName`
        }
    }
}