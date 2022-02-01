package com.nasa.apod.di

import com.nasa.apod.common.configuration.ApiConfig
import com.nasa.apod.landing.repository.APODRepository
import com.nasa.apod.landing.repository.impl.APODRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class])
class RepositoryModule {

    @Provides
    fun provideAPODRepository(apiConfig: ApiConfig): APODRepository {
        return APODRepositoryImpl(apiConfig)
    }
}