package br.com.aaascp.androidapp

import br.com.aaascp.androidapp.di.DaggerAppComponent
import br.com.aaascp.androidapp.di.module.ApplicationModule
import br.com.aaascp.androidapp.di.module.infra.DatabaseMockModule
import br.com.aaascp.androidapp.di.module.infra.EndpointMockModule

class MainApplicationDevelopment : MainApplication() {

    override fun initDagger() {
        component = DaggerAppComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .endpointModule(EndpointMockModule())
                .databaseModule(DatabaseMockModule(this))
                .build()
    }
}