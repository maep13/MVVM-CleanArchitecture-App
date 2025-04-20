package com.example.pokemonapp.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.example.pokemonapp.databasemodel.PokemonDao
import com.example.pokemonapp.databasemodel.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): PokemonDatabase {
        return Room.databaseBuilder(
                application,
                PokemonDatabase::class.java,
                "pokemon_db"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao {
        return database.pokemonDao()
    }
}