package com.example.pokemonapp.databasemodel

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon_details")
data class PokemonDetail(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String = "", // ✅ Evita `null`
    @TypeConverters(StringListConverter::class) // ✅ Convertir listas en Room
    @ColumnInfo(name = "types") val types: List<String>,
    @TypeConverters(StringListConverter::class)
    @ColumnInfo(name = "abilities") val abilities: List<String>,
    @TypeConverters(PokemonStatConverter::class) // ✅ Convertir `PokemonStat`
    @ColumnInfo(name = "stats") val stats: List<PokemonStat>
)

data class PokemonStat(
    @SerializedName("stat_name") val name: String,
    @SerializedName("base_stat") val baseStat: Int
)

// 📌 Convertidor para almacenar listas en Room
class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun toList(data: String): List<String> = data.split(",").map { it.trim() }
}

// 📌 Convertidor para `PokemonStat`
class PokemonStatConverter {
    @TypeConverter
    fun fromStats(list: List<PokemonStat>): String = list.joinToString("|") { "${it.name}:${it.baseStat}" }

    @TypeConverter
    fun toStats(data: String): List<PokemonStat> = data.split("|").map {
        val parts = it.split(":")
        PokemonStat(parts[0], parts[1].toInt())
    }
}