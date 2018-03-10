package br.com.aaascp.androidapp.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Singleton
    @Provides
    fun providesApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application.baseContext
    }
}

