package com.example.pokemonapp.databasemodel

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PokemonEntity::class, PokemonDetail::class], version = 2, exportSchema = false) // ✅ Agregamos `PokemonDetail` y aumentamos la versión
@TypeConverters(PokemonTypeConverter::class) // ✅ Se registran los convertidores para manejar listas
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao // ✅ DAO para gestionar los datos
}