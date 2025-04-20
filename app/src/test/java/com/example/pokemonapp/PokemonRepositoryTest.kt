package com.example.pokemonapp

import com.example.pokemonapp.networkmodel.ApiResult
import com.example.pokemonapp.networkmodel.PokemonApiService
import com.example.pokemonapp.networkmodel.PokemonResponse
import com.example.pokemonapp.networkmodel.PokemonResult
import com.example.pokemonapp.repository.PokemonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PokemonRepositoryTest {

    private lateinit var repository: PokemonRepository
    private val mockApiService = mock(PokemonApiService::class.java) // ✅ Simula la API con Mockito

    @Before
    fun setup() {
        repository = PokemonRepository(mockApiService) // ✅ Usa la API simulada en el repositorio
    }

    @Test
    fun `test successful API response`() = runBlocking {
        val fakeResponse = PokemonResponse(listOf(PokemonResult("Pikachu")))
        `when`(mockApiService.getPokemonList(1, 0)).thenReturn(fakeResponse) // ✅ Simula respuesta exitosa

        val result = repository.getPokemonList(1, 0)
        assert(result is ApiResult.Success)
        assertEquals("Pikachu", (result as ApiResult.Success).data.results.first().name)
    }

    @Test
    fun `test API error response`() = runBlocking {
        `when`(mockApiService.getPokemonList(1, 0)).thenThrow(RuntimeException("API Error")) // ❌ Simula un error

        val result = repository.getPokemonList(1, 0)
        assert(result is ApiResult.Error)
        assertEquals("API Error", (result as ApiResult.Error).message)
    }
}