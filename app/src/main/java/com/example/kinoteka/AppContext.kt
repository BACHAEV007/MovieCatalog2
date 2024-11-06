package com.example.kinoteka

import android.app.Application
import android.content.Context

class AppContext : Application() {

    companion object {
        lateinit var instance: AppContext
            private set

        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}