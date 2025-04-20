package com.example.pokemonapp.dependencyinjection

import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.viewmodel.PokemonViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun providePokemonViewModel(repository: PokemonRepository): PokemonViewModel {
        return PokemonViewModel(repository)
    }
}