package com.example.pokemonapp.networkmodel

sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>() // ✅ Agregar estado de carga
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}