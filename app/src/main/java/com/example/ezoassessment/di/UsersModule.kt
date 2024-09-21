package com.example.ezoassessment.di

import android.content.Context
import com.example.ezoassessment.data.Repository
import com.example.ezoassessment.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class UsersModule {

    @Binds
    abstract fun provideRepository(userRepository: UserRepository): Repository

}