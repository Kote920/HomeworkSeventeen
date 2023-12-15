package com.example.homeworkseventeen

import android.app.Application
import android.content.Context

class App: Application() {

    companion object{
        lateinit var application: Context
        private set
    }


    override fun onCreate() {
        super.onCreate()
        application = applicationContext
    }
}