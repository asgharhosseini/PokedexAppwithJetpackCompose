package ir.ah.pokedexappwithjetpackcompose.ui.screen.PokemonListScreen

import android.graphics.*
import android.graphics.drawable.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import androidx.palette.graphics.*
import dagger.hilt.android.lifecycle.*
import ir.ah.pokedexappwithjetpackcompose.data.models.*
import ir.ah.pokedexappwithjetpackcompose.repository.*
import ir.ah.pokedexappwithjetpackcompose.util.*
import ir.ah.pokedexappwithjetpackcompose.util.Constants.PAGE_SIZE
import kotlinx.coroutines.*
import java.time.temporal.*
import java.util.*
import javax.inject.*

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {
    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)


    init {
        loadPokemonPaginated()
    }


    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true

            val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when (result) {
                is Resource.Success ->{
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())

                    }
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

            }

        }


    }


    fun searchPokemonList(query: String){
        val listToSearch = if(isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                pokemonList.value=cachedPokemonList
                isSearching.value=false
                isSearchStarting=true
                return@launch
            }
            val result=listToSearch.filter {
                it.pokemonName.contains(query.trim(),ignoreCase = true)||
                        it.number.toString() ==query.trim()
            }
            if (isSearchStarting){
                cachedPokemonList=pokemonList.value
                isSearching.value=true
            }
            pokemonList.value = result
            isSearching.value = true

        }



    }
}