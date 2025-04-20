package com.example.pokemonapp.databasemodel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    fun getCachedPokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_details WHERE name = :pokemonName")
        suspend fun getPokemonDetail(pokemonName: String): PokemonDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonDetail(pokemonDetail: PokemonDetail)
}
