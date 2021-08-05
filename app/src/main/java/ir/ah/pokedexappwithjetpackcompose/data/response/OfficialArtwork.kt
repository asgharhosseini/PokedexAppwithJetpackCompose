package ir.ah.pokedexappwithjetpackcompose.data.response


import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)