package com.example.ezoassessment

import android.app.Application
import com.example.ezoassessment.di.AppComponent
import com.example.ezoassessment.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        provideDaggerComponent()
    }

    private fun provideDaggerComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}