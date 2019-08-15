package com.github.vccolombo.audiorecorder

import android.app.Application
import android.os.Environment
import com.github.vccolombo.audiorecorder.module.viewModelModules
import com.github.vccolombo.audiorecorder.utils.PATH_RECORDINGS_DIRECTORY
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.io.File


class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@MainApplication)
            // declare modules
            modules(viewModelModules)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}