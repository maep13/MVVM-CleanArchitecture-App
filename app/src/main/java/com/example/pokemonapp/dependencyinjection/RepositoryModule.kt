package com.example.pokemonapp.dependencyinjection

import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.networkmodel.PokemonApiService
import com.example.pokemonapp.databasemodel.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ✅ Se instala en el nivel Singleton
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        apiService: PokemonApiService,
        pokemonDao: PokemonDao // ✅ Conecta API y caché
    ): PokemonRepository {
        return PokemonRepository(apiService, pokemonDao)
    }
}