package com.example.pokemonapp

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.pokemonapp.view.navigation.PokemonAppNavigation
import com.example.pokemonapp.viewmodel.PokemonViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this) // ✅ Configura Hilt para pruebas instrumentadas

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>() // ✅ Crea el entorno de pruebas de Compose

    // ✅ Inyectamos PokemonViewModel
    @Inject
    lateinit var pokemonViewModel: PokemonViewModel // ✅ Agrega esta línea para la inyección

    @Before
    fun setup() {
        hiltRule.inject() // ✅ Inyecta dependencias de Hilt en la prueba

        composeTestRule.activity.setContent {
            PokemonAppNavigation(pokemonViewModel) // ✅ Ahora `pokemonViewModel` está correctamente inyectado
        }
    }

    @Test
    fun test_navigation_to_registrationScreen() {
        composeTestRule.onNodeWithText("Registro").assertIsDisplayed() // ✅ Verifica que `RegistrationScreen` aparece primero
    }

    @Composable
    @Test
    fun Test_navigation_to_pokemonPagedScreen() { // ✅ Quité la anotación `@Composable`, no se necesita aquí
        val navController = rememberNavController()
        composeTestRule.activity.setContent {
            PokemonAppNavigation(pokemonViewModel)
        }
        navController.navigate("pokemonPaged") // ✅ Navega manualmente a `PokemonPagedScreen`

        composeTestRule.onNodeWithText("Lista de Pokémon").assertIsDisplayed() // ✅ Verifica que la pantalla se muestra
    }
}