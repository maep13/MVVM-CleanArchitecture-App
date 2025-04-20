package com.example.pokemonapp

import com.example.pokemonapp.networkmodel.PokemonApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonApiServiceTest {

    private lateinit var apiService: PokemonApiService

    @Before
    fun setup() {
        val fakeInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val fakeJsonResponse = """
                    {"results":[{"name":"Pikachu","url":"https://pokeapi.co/api/v2/pokemon/25"}]}
                """.trimIndent()

                return Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .body(fakeJsonResponse.toResponseBody("application/json".toMediaType()))
                    .build()
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(fakeInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(PokemonApiService::class.java)
    }

    @Test
    fun testGetPokemonList() = runBlocking {
        val response = apiService.getPokemonList(limit = 1, offset = 0)
        Assert.assertEquals(
            "Pikachu",
            response.results.first().name
        ) // ✅ Verifica que la API devuelve Pikachu correctamente
    }
}