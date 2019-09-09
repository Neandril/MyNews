package com.neandril.mynews

import android.app.Application
import android.content.Context
import com.neandril.mynews.utils.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Entry point of the app.
 * This class seems to not be used, but it's a bug reported to Google
 * and it's still present in Android Studio 3.5
 * https://issuetracker.google.com/issues/74514347
 */
class ApplicationInit : Application() {

    private var appContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ApplicationInit)
            modules(listOf(applicationModule))
        }
    }

    fun getAppContext(): Context? {
        return appContext
    }
}