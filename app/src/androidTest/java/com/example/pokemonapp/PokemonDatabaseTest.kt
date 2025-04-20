@file:Suppress("DEPRECATION")

package com.example.pokemonapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.example.pokemonapp.databasemodel.PokemonDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.java
import com.example.pokemonapp.databasemodel.PokemonDatabase
import com.example.pokemonapp.databasemodel.PokemonEntity
import kotlinx.coroutines.flow.first

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PokemonDatabaseTest {

    private lateinit var database: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PokemonDatabase::class.java
        ).allowMainThreadQueries().build()

        pokemonDao = database.pokemonDao()
    }

    @Test
    fun testInsertAndRetrievePokemon() = runBlocking {
        val testPokemon = listOf(PokemonEntity(id = 1, name = "Pikachu"))

        // 📌 Guardar Pokémon en la base de datos
        pokemonDao.insertPokemon(testPokemon)

        // 📌 Recuperar Pokémon desde la base de datos
        val cachedPokemon = pokemonDao.getCachedPokemon().first()

        // 📌 Verificar que los datos recuperados son los mismos que los insertados
        assertEquals(testPokemon, cachedPokemon)
    }

    @After
    fun tearDown() {
        database.close() // 📌 Cerramos la base de datos al finalizar la prueba
    }
}