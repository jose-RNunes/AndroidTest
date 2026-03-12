package br.com.androidtest

import android.app.Application
import br.com.androidtest.di.appModuleClaro
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidTestApplication)
            modules(appModuleClaro)
        }
    }
}
