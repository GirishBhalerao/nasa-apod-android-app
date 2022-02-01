package com.nasa.apod.di

import com.nasa.apod.MainActivity
import com.nasa.apod.landing.fragment.APODFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindingModule {

    @ContributesAndroidInjector
    abstract fun bindingMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [RepositoryModule::class])
    abstract fun bindAPODFragment(): APODFragment
}