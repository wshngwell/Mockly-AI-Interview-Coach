package com.example.interviewaicoach.presentation

import android.app.Application
import com.example.interviewaicoach.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class InterViewAiCoachApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@InterViewAiCoachApp)
            modules(appModule)
        }
    }
}