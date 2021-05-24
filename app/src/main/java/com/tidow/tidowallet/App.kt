package com.tidow.tidowallet

import android.app.Application
import android.content.Context
import com.tidow.tidowallet.di.sharedPref
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(sharedPref))
        }
    }

}