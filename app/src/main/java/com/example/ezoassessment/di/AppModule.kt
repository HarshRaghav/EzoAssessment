package com.example.ezoassessment.di

import com.example.ezoassessment.data.Constants
import com.example.ezoassessment.data.network.APIService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.userBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService:: class.java)
    }
}