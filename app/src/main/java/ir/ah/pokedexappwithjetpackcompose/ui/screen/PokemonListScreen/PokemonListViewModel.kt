package ir.ah.pokedexappwithjetpackcompose.ui.screen.PokemonListScreen

import android.graphics.*
import android.graphics.drawable.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import androidx.palette.graphics.*
import dagger.hilt.android.lifecycle.*
import ir.ah.pokedexappwithjetpackcompose.repository.*
import javax.inject.*

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository):ViewModel() {

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit){
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}