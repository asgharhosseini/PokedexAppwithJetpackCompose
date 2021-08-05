package ir.ah.pokedexappwithjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import dagger.hilt.android.*
import ir.ah.pokedexappwithjetpackcompose.ui.screen.PokemonListScreen.*
import ir.ah.pokedexappwithjetpackcompose.ui.screen.pokemondetail.*
import ir.ah.pokedexappwithjetpackcompose.ui.theme.PokedexAppWithJetpackComposeTheme
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexAppWithJetpackComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen"
                ) {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        "pokemon_detail_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailScreen(
                            dominantColor = dominantColor,
                            pokemonName = pokemonName?.toLowerCase(Locale.ROOT) ?: "",
                            navController = navController
                        )
                    }

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokedexAppWithJetpackComposeTheme {
        val navController = rememberNavController()

                PokemonListScreen(navController = navController)


    }
}