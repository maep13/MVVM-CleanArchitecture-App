package com.example.pokemonapp.repository

import com.example.pokemonapp.databasemodel.PokemonDetail
import com.example.pokemonapp.databasemodel.PokemonStat
import com.example.pokemonapp.databasemodel.PokemonDao
import com.example.pokemonapp.databasemodel.PokemonEntity
import com.example.pokemonapp.networkmodel.ApiResult
import com.example.pokemonapp.networkmodel.PokemonApiService
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao // ✅ Se mantiene el DAO para caché
) {

    suspend fun getPokemonList(limit: Int = 20, offset: Int = 0): ApiResult<List<PokemonEntity>> {
        return runCatching {
            val response = apiService.getPokemonList(limit, offset) // ✅ Obtener datos de la API
            val pokemonEntities = response.results.map { PokemonEntity(id = it.name.hashCode(), name = it.name) }
            ApiResult.Success(pokemonEntities) // ✅ Retornar lista con solo nombres
        }.getOrElse { ApiResult.Error(it.message ?: "Error desconocido") } // ✅ Manejo de error optimizado
    }

    suspend fun getPokemonDetail(pokemonName: String): ApiResult<PokemonDetail> {
        return runCatching {
            // ✅ Consultar caché antes de llamar a la API
            pokemonDao.getPokemonDetail(pokemonName)?.let { return ApiResult.Success(it) }

            val response = apiService.getPokemonDetail(pokemonName)

            if (response.stats.isEmpty()) {
                println("Repo Debug - `stats` está vacío o `null`") // ✅ Verificación directa
            }

            val pokemonDetail = PokemonDetail(
                id = response.id,
                name = response.name,
                imageUrl = response.sprites.frontDefault.takeIf { it.isNotEmpty() }
                    ?: "https://example.com/default_image.png", // ✅ Imagen por defecto si falta
                types = response.types.map { it.type.name },
                abilities = response.abilities.map { it.ability.name },
                stats = response.stats.map { statEntry ->
                    statEntry.stat.let {
                        val statName = it.name // ✅ Asegurar que `stat.name` nunca sea null
                        val statValue = statEntry.baseStat.takeIf { it > 0 } ?: -1 // ✅ Evita `0`

                        println("Repo Debug - Stat Name: $statName, Valor: $statValue") // ✅ Depuración extra
                        println("Debug - ImageUrl en API: ${response.sprites.frontDefault}") // ✅ Verifica si la API envía correctamente la imagen
                        response.stats.forEach { println("Debug - API Stat: ${it.stat.name}, Valor: ${it.baseStat}") } // ✅ Verifica si los `baseStat` llegan con valores distintos de `0`
                        PokemonStat(name = statName, baseStat = statValue)
                    }
                }
            )

            println("Repo Debug - ImageUrl: ${pokemonDetail.imageUrl}") // ✅ Verifica si la imagen llega correctamente

            pokemonDao.insertPokemonDetail(pokemonDetail) // ✅ Guardar en caché
            ApiResult.Success(pokemonDetail) // ✅ Retornar datos correctamente
        }.getOrElse { ApiResult.Error(it.message ?: "Error desconocido") } // ✅ Manejo de error optimizado
    }
}