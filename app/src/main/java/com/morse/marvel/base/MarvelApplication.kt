package com.morse.marvel.base

import android.app.Application
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.morse.marvel.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.logging.LogManager
import java.util.logging.Logger

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                appModule
            )
            androidContext(this@MarvelApplication)
            androidLogger(Level.INFO)
        }

    }

}