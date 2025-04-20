package com.example.pokemonapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.databasemodel.PokemonDetail
import com.example.pokemonapp.networkmodel.ApiResult
import com.example.pokemonapp.networkmodel.PokemonResult
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.viewmodel.PokemonState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonState = MutableStateFlow<PokemonState>(Loading) // ✅ Importado correctamente
    val pokemonState = _pokemonState.asStateFlow()
    private val currentPokemonList = mutableListOf<PokemonResult>() // ✅ Lista acumulativa
    private var currentOffset = 0 // ✅ Para manejar la paginación
    private var isLoading = false // ✅ Variable para evitar múltiples llamadas simultáneas

    fun fetchPokemonList(limit: Int = 20) {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            if (currentPokemonList.isEmpty()) _pokemonState.value = Loading

            when (val result = repository.getPokemonList(limit, currentOffset)) {
                is ApiResult.Success -> {
                    val updatedList = result.data.map {
                        PokemonResult(name = it.name) // ✅ Solo `name`, sin `imageUrl`, `types` ni `url`
                    }
                    currentPokemonList.addAll(updatedList)
                    currentOffset += limit
                    _pokemonState.value = Success(currentPokemonList) // ✅ Notificar correctamente
                }
                is ApiResult.Error -> {
                    _pokemonState.value = if (currentPokemonList.isNotEmpty())
                        Success(currentPokemonList)
                    else
                        Error(result.message)
                }

                ApiResult.Loading -> TODO()
            }
            isLoading = false
        }
    }

    fun fetchPokemonDetail(pokemonName: String): Flow<ApiResult<PokemonDetail>> {
        return flow {
            _pokemonState.value =
                Loading // ✅ Usa `PokemonState.Loading` en lugar de `ApiResult.Loading()`
            val result = repository.getPokemonDetail(pokemonName) // ✅ Obtener datos desde el repositorio
            emit(result) // ✅ Emitir el resultado correctamente
        }.flowOn(Dispatchers.IO)
    }
}
// 📌 Modelos de estado para manejar carga, éxito y error
sealed class PokemonState {
    object Loading : PokemonState() // ✅ Importado correctamente
    data class Success(val pokemonList: List<PokemonResult>) : PokemonState()
    data class Error(val message: String) : PokemonState()
}
