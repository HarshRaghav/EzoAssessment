package com.example.ezoassessment.di

import androidx.lifecycle.ViewModel
import com.example.ezoassessment.ui.viewmodels.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel:: class)
    abstract fun provideViewModel(viewModel: UserViewModel): ViewModel

}