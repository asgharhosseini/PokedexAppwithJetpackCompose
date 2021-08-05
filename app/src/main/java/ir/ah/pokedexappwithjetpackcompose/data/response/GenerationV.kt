package ir.ah.pokedexappwithjetpackcompose.data.response


import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val blackWhite: BlackWhite
)