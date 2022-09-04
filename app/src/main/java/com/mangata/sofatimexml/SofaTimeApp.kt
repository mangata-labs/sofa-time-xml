package com.mangata.sofatimexml

import android.app.Application
import com.mangata.sofatimexml.di.appModule
import com.mangata.tvshow_data.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SofaTimeApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SofaTimeApp)
            modules(remoteModule)
            modules(appModule)
        }
    }
}