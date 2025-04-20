package com.example.pokemonapp.networkmodel

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonResponse

    // ✅ Nuevo endpoint para obtener detalles de un Pokémon específico
    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") pokemonName: String
    ): PokemonDetailResponse
}