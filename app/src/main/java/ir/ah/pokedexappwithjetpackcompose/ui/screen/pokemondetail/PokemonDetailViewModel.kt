package ir.ah.pokedexappwithjetpackcompose.ui.screen.pokemondetail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import ir.ah.pokedexappwithjetpackcompose.data.response.*
import ir.ah.pokedexappwithjetpackcompose.repository.*
import ir.ah.pokedexappwithjetpackcompose.util.*
import javax.inject.*


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository):ViewModel(){


    suspend fun getPokemonInfo(pokemonName:String):Resource<Pokemon>{
        return repository.getPokemonInfo(pokemonName = pokemonName)
    }
}