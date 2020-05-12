package com.maku.weather

import android.app.Application
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class Weather : Application(){
    override fun onCreate() {
        super.onCreate()
        //timber
        Timber.plant(Timber.DebugTree())

        //Threeten
        AndroidThreeTen.init(this)

    }
}
