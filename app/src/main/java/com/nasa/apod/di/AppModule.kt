package com.nasa.apod.di

import android.content.Context
import com.google.gson.Gson
import com.nasa.apod.MainApplication
import com.nasa.apod.common.configuration.ApiConfig
import com.nasa.apod.common.util.FileHelper
import com.nasa.apod.landing.data.APODApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: MainApplication): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideApiConfig(context: Context): ApiConfig {
        return Gson().fromJson(
            FileHelper.loadJSONFromAsset(context, "apiConfig.json"),
            ApiConfig::class.java
        )
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): APODApiService {
        return retrofit.create(APODApiService::class.java)
    }
}