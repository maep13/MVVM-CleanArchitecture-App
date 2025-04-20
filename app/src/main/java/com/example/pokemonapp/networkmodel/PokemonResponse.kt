package com.example.pokemonapp.networkmodel

data class PokemonResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String
    // ✅ Imagen del Pokémon
)