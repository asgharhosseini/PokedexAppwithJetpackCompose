package ir.ah.pokedexappwithjetpackcompose

import android.app.*
import dagger.hilt.android.*
import timber.log.*

@HiltAndroidApp
class PokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}