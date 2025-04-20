package com.example.pokemonapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Aquí se pueden inicializar configuraciones globales si es necesario
    }
}