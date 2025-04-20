package com.example.pokemonapp

import com.example.pokemonapp.networkmodel.ApiResult
import com.example.pokemonapp.networkmodel.PokemonResponse
import com.example.pokemonapp.networkmodel.PokemonResult
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.viewmodel.PokemonState
import com.example.pokemonapp.viewmodel.PokemonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonViewModelTest {

    private lateinit var viewModel: PokemonViewModel
    private val mockRepository = mockk<PokemonRepository>() // ✅ Usamos MockK para evitar problemas con clases finales
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // ✅ Configuramos el dispatcher de pruebas
        viewModel = PokemonViewModel(mockRepository) // ✅ Inicializamos `PokemonViewModel`
    }

    @Test
    fun `test successful API response`() = runTest {
        val fakePokemonResponse = PokemonResponse(listOf(PokemonResult("Pikachu")))
        coEvery { mockRepository.getPokemonList(1, 0) } returns ApiResult.Success(fakePokemonResponse) // ✅ Simula respuesta exitosa

        viewModel.fetchPokemonList(1, 0)

        val state = viewModel.pokemonState.first()
        assert(state is PokemonState.Success)
        assertEquals("Pikachu", (state as PokemonState.Success).pokemonList.first().name)
    }

    @Test
    fun `test API error response`() = runTest {
        coEvery { mockRepository.getPokemonList(1, 0) } returns ApiResult.Error("API Error") // ❌ Simula un error

        viewModel.fetchPokemonList(1, 0)

        val state = viewModel.pokemonState.first()
        assert(state is PokemonState.Error)
        assertEquals("API Error", (state as PokemonState.Error).message)
    }
}
