package com.example.pokemonapp.networkmodel

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<TypeWrapper>,
    val abilities: List<AbilityWrapper>,
    val stats: List<StatWrapper>
)

data class Sprites(val frontDefault: String)

data class TypeWrapper(val type: Type)
data class Type(val name: String)

data class AbilityWrapper(val ability: Ability)
data class Ability(val name: String)

data class StatWrapper(val stat: Stat, val baseStat: Int)
data class Stat(val name: String)