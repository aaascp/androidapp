package br.com.aaascp.androidapp

import android.app.Application
import br.com.aaascp.androidapp.di.AppComponent
import br.com.aaascp.androidapp.di.DaggerAppComponent
import br.com.aaascp.androidapp.di.module.ApplicationModule
import br.com.aaascp.androidapp.di.module.infra.DatabaseModule

open class MainApplication : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    protected open fun initDagger() {
        component = DaggerAppComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .databaseModule(DatabaseModule(this))
                .build()
    }
}