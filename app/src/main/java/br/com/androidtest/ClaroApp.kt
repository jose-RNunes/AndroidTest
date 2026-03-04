package br.com.androidtest

import android.app.Application
import br.com.androidtest.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ClaroApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ClaroApp)
            properties(
                mapOf(
                    "privacy_url" to "https://www.lipsum.com/",
                ),
            )
            modules(appModules)
        }
    }
}