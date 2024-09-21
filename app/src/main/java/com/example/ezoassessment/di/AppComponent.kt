package com.example.ezoassessment.di

import android.content.Context
import com.example.ezoassessment.MainActivity
import com.example.ezoassessment.ProfileActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UsersModule::class ,AppModule::class,ViewModelModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    companion object{
        fun factory(): Factory = DaggerAppComponent.factory()
    }

    fun inject(activity: MainActivity)

    fun inject(activity: ProfileActivity)
}