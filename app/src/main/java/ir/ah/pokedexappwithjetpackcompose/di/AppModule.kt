package ir.ah.pokedexappwithjetpackcompose.di

import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ah.pokedexappwithjetpackcompose.data.remote.*
import ir.ah.pokedexappwithjetpackcompose.repository.*
import ir.ah.pokedexappwithjetpackcompose.util.Constants.BASE_URL
import retrofit2.*
import retrofit2.converter.gson.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}