package ir.ah.pokedexappwithjetpackcompose.data.response

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)