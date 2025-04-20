package com.example.pokemonapp.databasemodel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return gson.toJson(list) // ✅ Convierte la lista en formato JSON para almacenarla
    }

    @TypeConverter
    fun fromStringToList(data: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, type) // ✅ Convierte el JSON nuevamente en una lista
    }

    @TypeConverter
    fun fromStatsListToString(stats: List<PokemonStat>): String {
        return gson.toJson(stats) // ✅ Convierte `List<PokemonStat>` en JSON
    }

    @TypeConverter
    fun fromStringToStatsList(data: String): List<PokemonStat> {
        val type = object : TypeToken<List<PokemonStat>>() {}.type
        return gson.fromJson(data, type) // ✅ Convierte el JSON en `List<PokemonStat>`
    }
}