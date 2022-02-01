package com.nasa.apod.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nasa.apod.landing.viewmodel.APODViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(APODViewModel::class)
    abstract fun bindAPODViewModel(aPodViewModel: APODViewModel): ViewModel

    @Binds
    abstract fun bindAPODViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}